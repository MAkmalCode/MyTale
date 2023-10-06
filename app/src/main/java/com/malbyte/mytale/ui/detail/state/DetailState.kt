package com.malbyte.mytale.ui.detail.state

import com.malbyte.mytale.data.source.remote.models.Story

sealed class DetailState {
    data object Loading : DetailState()
    data class Error(val message: String) : DetailState()
    data class Success(val story: Story) : DetailState()
}