package com.innowi.android.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.innowi.android.R
import com.innowi.android.domain.model.photo.response.PhotoResponse
import com.innowi.android.domain.usecase.GetPhotosUseCase
import com.innowi.android.util.isInternetAvailable
import com.innowi.android.util.network.NetworkException
import com.innowi.android.util.network.NetworkResult
import com.innowi.android.util.network.exceptionHandler
import com.innowi.android.util.network.launchApi
import com.innowi.android.util.storage.MySharePreference.getSavedData
import com.innowi.android.util.storage.MySharePreference.saveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val application: Application,
    private val photosUseCase: GetPhotosUseCase
) : AndroidViewModel(application) {

    var result = MutableLiveData<NetworkResult<PhotoResponse>>()
    var noInternetConnection = MutableLiveData<Boolean>()

    init {
        getPhotoData()
    }

    private fun getPhotoData() {
        if (application.isInternetAvailable()) {
            result.value = NetworkResult.Loading()
            val exceptionHandler = viewModelScope.exceptionHandler {
                if (it is NetworkException)
                    result.value = NetworkResult.Error(errorResponse = it.errorResponse)
            }

            viewModelScope.launchApi(exceptionHandler) {
                val response = photosUseCase.run()
                saveData(response)
                result.value = NetworkResult.Success(result = response)
            }
        } else {
            noInternetConnection.value = true
            result.value = NetworkResult.Success(result = getOfflineResponse())
        }
    }


    private fun getOfflineResponse(): ArrayList<PhotoResponse>? {
        try {
            if (!getSavedData().isNullOrEmpty()) {
                val gson = Gson()
                val response = object : TypeToken<ArrayList<PhotoResponse>>() {}.type
                return gson.fromJson(getSavedData(), response)
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return ArrayList()
    }

    fun messageStatus(status: Boolean){
        noInternetConnection.value = status
    }
}