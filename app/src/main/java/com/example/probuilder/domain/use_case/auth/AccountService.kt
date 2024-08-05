package com.example.probuilder.domain.use_case.auth

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.util.trace
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialCustomException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.CreateCredentialInterruptedException
import androidx.credentials.exceptions.CreateCredentialProviderConfigurationException
import androidx.credentials.exceptions.CreateCredentialUnknownException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.publickeycredential.CreatePublicKeyCredentialDomException
import com.example.probuilder.presentation.screen.sign_up.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountService @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleAuthService: GoogleAuthService,
    private val credentialManager: CredentialManager,
    private val context: Context
)  : CoroutineScope by CoroutineScope(Dispatchers.Main) {

    val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    val hasUser: Boolean
        get() = auth.currentUser != null

    val currentUser: Flow<UserData>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let {
                        UserData(it.uid, it.email.orEmpty(), it.photoUrl.toString()) } ?: UserData())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    suspend fun startGoogleSignIn() {
        val isSignedIn = googleAuthService.signInWithGoogle(auth::signInWithCredential)
        if (isSignedIn) "Sinn up success!".showToast()
    }
    fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                saveCredentials(email, password)
                "Sinn up success! ${user?.email}".showToast()
            } else {
                "Authentication failed.".showToast()
            }
        }
    }

    private fun saveCredentials(email: String, password: String) {
        val createPasswordRequest = CreatePasswordRequest(id = email, password = password)
        launch {
            try {
                val result = credentialManager.createCredential(context, createPasswordRequest)
            } catch (e: CreateCredentialException) {
                handleFailure(e)
            }
        }
    }

    suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                "Authentication success.".showToast()
            } else {
                "Authentication failed.".showToast()
            }
        }
    }

    suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }



    suspend fun refreshToken() {
        try {
            val getPasswordOption = GetPasswordOption() // Retrieves the user's saved password for your app
            val credentialResponse = credentialManager.getCredential(
                request = GetCredentialRequest(listOf(getPasswordOption)),
                context = context,
            )
            val credentials = credentialResponse.credential
            auth.signInWithCredential(credentials as AuthCredential)
            val user = auth.currentUser
            "Credantial are proided!".showToast()
        } catch (e: GetCredentialException) {
            if (e.type.contains("TYPE_NO_CREDENTIAL")) {
                e.message?.showToast()
            } else {
                "Some error happend".showToast()
            }
        }
    }





    fun getUsr(){
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
        }
    }

    suspend fun linkAccount(email: String, password: String): Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser!!.linkWithCredential(credential).await()
        }

    suspend fun deleteAccount() {
        try {
            auth.currentUser?.delete()
            "Successfully deleted account".showToast()
        } catch (e: Exception) {
            "Delete account error. ${e.message.toString()}".showToast()
//            auth.signIn
        }
    }

    suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()
        credentialManager.clearCredentialState(ClearCredentialStateRequest())

        // Sign the user back in anonymously.
        createAnonymousAccount()
    }

    fun getUserData(): UserData? {
        return auth.currentUser?.run { UserData(uid, displayName.orEmpty(), photoUrl.toString()) }
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }

    private fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
        if (this.isNotEmpty()) Toast.makeText(context, this, duration).show()
    }


    private fun handleFailure(e: CreateCredentialException) {
        when (e) {
            is CreatePublicKeyCredentialDomException -> {
                // Handle the passkey DOM errors thrown according to the
                // WebAuthn spec.
//                handlePasskeyError(e.domError)
            }
            is CreateCredentialCancellationException -> {
                // The user intentionally canceled the operation and chose not
                // to register the credential.
            }
            is CreateCredentialInterruptedException -> {
                // Retry-able error. Consider retrying the call.
            }
            is CreateCredentialProviderConfigurationException -> {
                // Your app is missing the provider configuration dependency.
                // Most likely, you're missing the
                // "credentials-play-services-auth" module.
            }
            is CreateCredentialUnknownException -> TODO()
            is CreateCredentialCustomException -> {
                // You have encountered an error from a 3rd-party SDK. If you
                // make the API call with a request object that's a subclass of
                // CreateCustomCredentialRequest using a 3rd-party SDK, then you
                // should check for any custom exception type constants within
                // that SDK to match with e.type. Otherwise, drop or log the
                // exception.
            }
            else -> Log.w(TAG, "Unexpected exception type ${e::class.java.name}")
        }
    }
}