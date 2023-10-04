package com.malbyte.mytale.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malbyte.mytale.data.appPref.GlobalPref
import com.malbyte.mytale.data.source.remote.service.APIInterface
import com.malbyte.mytale.ui.login.state.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val apiInterface: APIInterface
) : ViewModel() {
    private val _LoginStatus = MutableLiveData<LoginState>(null)
    val LoginStatus: LiveData<LoginState> = _LoginStatus

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _LoginStatus.postValue(
                LoginState.Loading
            )
            try {
                val result = apiInterface.loginUser(
                    email,
                    password
                )
                if(!result.error){
                    _LoginStatus.postValue(
                        LoginState.Success
                    )
                }
                GlobalPref.token = result.loginResult.token
            }catch (e: Exception){
                _LoginStatus.postValue(
                    LoginState.Error(e.message.toString())
                )
            }
        }
    }
}