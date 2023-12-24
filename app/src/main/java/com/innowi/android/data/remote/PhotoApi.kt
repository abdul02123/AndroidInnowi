package com.innowi.android.data.remote

import com.innowi.android.domain.model.photo.response.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET

interface PhotoApi {

    @GET("photos")
    suspend fun getPhotos() : Response<ArrayList<PhotoResponse>>
}