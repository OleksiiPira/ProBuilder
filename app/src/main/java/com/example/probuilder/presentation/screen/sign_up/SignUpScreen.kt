package com.example.probuilder.presentation.screen.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.BasicButton
import com.example.probuilder.presentation.components.EmailField
import com.example.probuilder.presentation.components.PasswordField
import com.example.probuilder.presentation.components.RepeatPasswordField

import com.example.probuilder.R.string as AppText

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit = { str1, str2 -> println(str1 + str2) },
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val user by viewModel.currentUser.collectAsState(initial = UserData())

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
        startGoogleSignIn = viewModel::startGoogleSignIn,
        signOut = viewModel::signOut,
        deleteAccount = viewModel::deleteAccount,
        user = user
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    startGoogleSignIn: () -> Unit,
    deleteAccount: () -> Unit,
    signOut: () -> Unit,
    user: UserData,
) {
    val fieldModifier = Modifier.fieldModifier()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = user.toString())
        EmailField(uiState.email, onEmailChange, fieldModifier)
        PasswordField(uiState.password, onPasswordChange, fieldModifier)
        RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, fieldModifier)

        BasicButton(AppText.create_account, Modifier.basicButton()) {
            onSignUpClick()
        }

        Button(onClick = startGoogleSignIn) { Text(text = "Login with Google") }
        Button(onClick = signOut) { Text(text = "Sign Out") }
        Button(onClick = deleteAccount) { Text(text = "Delete Account") }

    }
}

fun Modifier.fieldModifier(): Modifier {
    return this
        .fillMaxWidth()
        .padding(16.dp, 4.dp)
}

fun Modifier.basicButton(): Modifier {
    return this
        .fillMaxWidth()
        .padding(16.dp, 8.dp)
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )
//        SignUpScreenContent(
//            uiState = uiState,
//            onEmailChange = { },
//            onPasswordChange = { },
//            onRepeatPasswordChange = { },
//            onSignUpClick = { },
//            startGoogleSignIn = {},
//            user = UserData("","",""),
//            signOut =
//        )
}
