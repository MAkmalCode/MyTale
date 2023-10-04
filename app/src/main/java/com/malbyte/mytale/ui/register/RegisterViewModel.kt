package com.malbyte.mytale.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malbyte.mytale.data.source.remote.service.APIInterface
import com.malbyte.mytale.ui.register.state.RegisterState
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val apiInterface: APIInterface
):ViewModel() {
    private val _LoadingStatus = MutableLiveData<RegisterState>(null)
    val LoadingStatus: LiveData<RegisterState> = _LoadingStatus

    fun register (
        name: String,
        email : String,
        password: String
    ){
        viewModelScope.launch {
            _LoadingStatus.postValue(
                RegisterState.Loading
            )
            try {
                val result = apiInterface.registerUser(
                    name,
                    email,
                    password
                )
                if(!result.error){
                    _LoadingStatus.postValue(
                        RegisterState.Success
                    )
                }
            }catch (e : Exception){
                _LoadingStatus.postValue(
                    RegisterState.Error(e.message.toString())
                )
            }
        }
    }
}