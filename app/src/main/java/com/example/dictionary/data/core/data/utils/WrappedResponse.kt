package com.example.dictionary.data.core.data.utils

data class WrappedResponse<T>(
    var code: Int,
    var status: String,
    var data: T
)

data class WrappedErrorResponse(
    var code: String,
    var message: String,
)

data class ErrorResponse(
    val message: String
)