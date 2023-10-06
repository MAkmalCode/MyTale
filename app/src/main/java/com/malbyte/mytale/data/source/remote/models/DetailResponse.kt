package com.malbyte.mytale.data.source.remote.models


import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("story")
    val story: Story
)