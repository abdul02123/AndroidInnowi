package com.innowi.android.util.network

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("http_response")
    var httpCode: Int? = 0,
    @SerializedName("code")
    val code: Int? = 0,
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    var message: String? = ""
)