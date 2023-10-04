package com.malbyte.mytale.ui.login.state


sealed class LoginState {
    data object Loading : LoginState()
    data class Error(val message: String) : LoginState()
    data object Success : LoginState()
}