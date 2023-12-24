package com.innowi.android.util.network

import com.google.gson.Gson
import com.innowi.android.R
import com.innowi.android.base.BaseApplication
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


fun <T> errorResponse(response: Response<T>): Nothing {

    var errorResponse = ErrorResponse()
    if (response.code() in 500..510) {
        errorResponse.message =
            BaseApplication.getInstance().getString(R.string.network_error_message_502)
        throw NetworkException(errorResponse)
    } else if (response.code() in 401..403) {
        errorResponse.message =
            BaseApplication.getInstance().getString(R.string.network_error_message_403)
        throw NetworkException(errorResponse)
    }

    try {
        val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
        errorResponse = Gson().fromJson(jObjError.toString(), ErrorResponse::class.java)
        errorResponse.httpCode = response.code()
        throw NetworkException(errorResponse)
    } catch (exception: JSONException) {
        exception.printStackTrace()
        errorResponse.httpCode = response.code()
        errorResponse.message =
            BaseApplication.getInstance().getString(R.string.network_something_wrong)
        throw NetworkException(errorResponse)
    }
}
