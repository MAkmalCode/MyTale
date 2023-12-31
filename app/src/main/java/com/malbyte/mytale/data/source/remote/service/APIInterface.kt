package com.malbyte.mytale.data.source.remote.service

import com.malbyte.mytale.data.appPref.GlobalPref
import com.malbyte.mytale.data.source.remote.models.AddStoryResponse
import com.malbyte.mytale.data.source.remote.models.DetailResponse
import com.malbyte.mytale.data.source.remote.models.LoginResponse
import com.malbyte.mytale.data.source.remote.models.RegisterResponse
import com.malbyte.mytale.data.source.remote.models.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.File

interface APIInterface {
    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun allStories(
        @Header("Authorization") authorization: String = "Bearer ${GlobalPref.token}"
    ): StoriesResponse

    @GET("stories/{id}")
    suspend fun getDetail(
        @Path("id") id: String,
        @Header("Authorization") authorization: String = "Bearer ${GlobalPref.token}"
    ): DetailResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part?,
        @Part("lat") latitude: Float?,
        @Part("lon") longitude: Float?,
        @Header("Authorization") authorization: String = "Bearer ${GlobalPref.token}"
    ) : AddStoryResponse
}