package com.innowi.android.util.network

data class ErrorResponse(
    var httpCode: Int? = 0,
    var code: Int? = 0,
    var success: Boolean = false,
    var message: String? = ""
)