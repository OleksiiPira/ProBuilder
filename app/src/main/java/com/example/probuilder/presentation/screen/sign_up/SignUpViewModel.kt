/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.probuilder.presentation.screen.sign_up

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.domain.use_case.auth.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,

    private val credentialManager: CredentialManager,
//    private val resource: Resources
) : ViewModel() {
    var uiState = mutableStateOf(SignUpUiState())
        private set
    var currentUser: Flow<UserData> = accountService.currentUser

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)

    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            uiState.value = uiState.value.copy(errorMessage = "resource.getString(AppText.email_error)")
        }

        if (!password.isValidPassword()) {
            uiState.value = uiState.value.copy(errorMessage = "resource.getString(AppText.password_error)")
//            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            uiState.value = uiState.value.copy(errorMessage = "resource.getString(AppText.password_match_error)")
//            return
        }

        viewModelScope.launch {
            signUpWithEmailAndPassword(email, password)
            openAndPopUp("SETTINGS_SCREEN", "SIGN_UP_SCREEN")
            accountService.getUsr()
        }
    }

    fun startGoogleSignIn() { viewModelScope.launch { accountService.startGoogleSignIn() } }

    fun signUpWithEmailAndPassword(email: String, password: String) {
        accountService.createUserWithEmailAndPassword(email, password)
    }

    fun deleteAccount() { viewModelScope.launch { accountService.deleteAccount() } }
    fun signOut() { viewModelScope.launch { accountService.signOut() } }



    fun getUserData(): UserData? {
        return accountService.getUserData()
    }

    private val MIN_PASS_LENGTH = 6
    private val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

    private fun String.isValidEmail(): Boolean {
        return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun String.isValidPassword(): Boolean {
        return this.isNotBlank() &&
                this.length >= MIN_PASS_LENGTH &&
                Pattern.compile(PASS_PATTERN).matcher(this).matches()
    }

    private fun String.passwordMatches(repeated: String): Boolean {
        return this == repeated
    }

    fun String.idFromParameter(): String {
        return this.substring(1, this.length - 1)
    }
}
