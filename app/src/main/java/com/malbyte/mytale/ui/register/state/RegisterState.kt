package com.malbyte.mytale.ui.register.state

sealed class RegisterState {
    data object Loading : RegisterState()
    data class Error(val message: String) : RegisterState()
    data object Success : RegisterState()
}