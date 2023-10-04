package com.malbyte.mytale.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malbyte.mytale.data.source.remote.service.APIInterface
import com.malbyte.mytale.ui.home.state.HomeState
import kotlinx.coroutines.launch

class HomeVIewModel(
    private val apiInterface: APIInterface
): ViewModel() {
    private val _StoryState = MutableLiveData<HomeState>(null)
    val StoryState: LiveData<HomeState> = _StoryState

    fun getAllStory(){
        viewModelScope.launch {
            _StoryState.postValue(
                HomeState.Loading
            )
            try {
                val result = apiInterface.allStories()

                if(!result.error){
                    _StoryState.postValue(
                        HomeState.Success(result.listStory)
                    )
                }
            }catch (e: Exception){
                _StoryState.postValue(
                    HomeState.Error(e.message.toString())
                )
            }
        }
    }
}