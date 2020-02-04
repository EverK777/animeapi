package com.example.applaudochallange.data.external

import com.example.applaudochallange.data.external.responses.ErrorResponse

sealed class ApiResultHandle<out T> {
    data class Success<out T>(val value:T): ApiResultHandle<T>()
    data class ApiError(val code: String = "", val error : ErrorResponse? = null): ApiResultHandle<Nothing>()
    object NetworkError: ApiResultHandle<Nothing>()
}