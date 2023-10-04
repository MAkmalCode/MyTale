package com.malbyte.mytale.ui.home.state

import com.malbyte.mytale.data.source.remote.models.Story

sealed class HomeState {
    data object Loading : HomeState()
    data class Error(val message: String) : HomeState()
    data class Success(val list: List<Story>) : HomeState()
}