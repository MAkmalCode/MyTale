package com.malbyte.mytale.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malbyte.mytale.data.source.remote.service.APIInterface
import com.malbyte.mytale.ui.detail.state.DetailState
import kotlinx.coroutines.launch

class DetailViewModel(
    private val apiInterface: APIInterface
): ViewModel() {
    private val _detailState = MutableLiveData<DetailState>(null)
    val detailState = _detailState

    fun getDetail(
        id: String
    ){
        viewModelScope.launch {
            _detailState.postValue(
                DetailState.Loading
            )
            try {
                val result = apiInterface.getDetail(id)
                _detailState.postValue(
                    DetailState.Success(result.story)
                )
            }catch (e: Exception){
                _detailState.postValue(
                    DetailState.Error(e.message.toString())
                )
            }
        }
    }
}