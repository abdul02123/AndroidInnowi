package com.innowi.android.data.repository

import com.innowi.android.data.remote.PhotoApi
import com.innowi.android.domain.model.photo.response.PhotoResponse
import com.innowi.android.util.network.errorResponse
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val photoApi: PhotoApi) {

    suspend fun getAllPhotos(): ArrayList<PhotoResponse>{
        val response = photoApi.getPhotos()
        return if (response.isSuccessful){
            requireNotNull(response.body())
        } else{
            errorResponse(response)
        }
    }
}