package com.trackfox.app.network

import com.trackfox.app.data.dto.ErrorDTO

sealed interface NetworkResult<out T> {
    data class Success<out T> (val data : T) : NetworkResult<T>
    data class APIError(val code : Int, val error : ErrorDTO? = null) : NetworkResult<Nothing>
    data class ConnectionError(val throwable: Throwable) : NetworkResult<Nothing>
}