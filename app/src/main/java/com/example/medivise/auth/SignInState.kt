package com.example.medivise.auth

data class SignInState(
    val isSignInSuccessful: Boolean=false,
    val signInError: String? =null
)