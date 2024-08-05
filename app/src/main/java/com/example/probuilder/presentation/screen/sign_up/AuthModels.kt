package com.example.probuilder.presentation.screen.sign_up

data class SignInResult(
    val userData: UserData?,
    val errorMessage: String?
)

data class UserData(
    val id: String = "",
    val name: String = "",
    val profileUrl: String = ""
)