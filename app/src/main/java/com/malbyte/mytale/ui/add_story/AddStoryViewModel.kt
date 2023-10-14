package com.malbyte.mytale.ui.add_story

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malbyte.mytale.data.source.remote.service.APIInterface
import com.malbyte.mytale.ui.add_story.state.AddStoryState
import com.malbyte.mytale.utils.buildImageBodyPart
import com.malbyte.mytale.utils.convertToFile
import com.malbyte.mytale.utils.setRequestBody
import kotlinx.coroutines.launch

class AddStoryViewModel(
    private val apiInterface: APIInterface,
    application: Application
) : AndroidViewModel(application) {

    val context = application.applicationContext

    private val _addStoryState = MutableLiveData<AddStoryState>(null)
    val addStoryState: LiveData<AddStoryState> = _addStoryState

    private val _photo = MutableLiveData<Bitmap>(null)
    val photo: LiveData<Bitmap> = _photo

    fun setPhoto(newPhoto: Bitmap) = _photo.postValue(newPhoto)
    fun addStory(
        description: String,
        lat: Float?,
        long: Float?
    ) {
        val file = _photo.value?.convertToFile(context, description)
        viewModelScope.launch {
            _addStoryState.postValue(
                AddStoryState.Loading
            )
            try {
                val response = apiInterface.addStory(
                    description.setRequestBody(),
                    file?.buildImageBodyPart(),
                    lat,
                    long
                )
                if (!response.error) {
                    _addStoryState.postValue(
                        AddStoryState.Success
                    )
                }
            } catch (e: Exception) {
                _addStoryState.postValue(
                    AddStoryState.Error(e.message.toString())
                )
            }
        }
    }
}