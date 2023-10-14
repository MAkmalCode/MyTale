package com.malbyte.mytale.ui.add_story.state
sealed class AddStoryState {
    data object Loading : AddStoryState()
    data class Error(val message: String) : AddStoryState()
    object Success : AddStoryState()
}
