package com.example.probuilder.domain.use_case.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.probuilder.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class GoogleAuthService @Inject constructor(
    private val context: Context
) {
    private val credentialManager by lazy { CredentialManager.create(context) }
    private val getGoogleIdOption by lazy { GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setNonce(getNonce())
            .build()
    }
    private val getCredentialRequest by lazy { GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()
    }

    suspend fun signInWithGoogle(login: (AuthCredential) -> Unit): Boolean {
        return try {
            val credentialResult = credentialManager.getCredential(context, getCredentialRequest)
            val credential = credentialResult.credential

            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)

            val googleIdToken = googleIdTokenCredential.idToken
            val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            login(googleCredential)
            true
        } catch (e: Exception) {
            handleError(e)
            false
        }
    }

    private fun getNonce(): String{
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it)}
        return hashedNonce
    }

    private fun handleError(e: Exception) {
        when (e) {
            is GetCredentialException, is GoogleIdTokenParsingException -> {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Log.e("GoogleAuthService", "Error: ${e.message}", e)
            }
            else -> {
                Toast.makeText(context, "Unexpected error occurred.", Toast.LENGTH_SHORT).show()
                Log.e("GoogleAuthService", "Unexpected error: ${e.message}", e)
            }
        }
    }
}
