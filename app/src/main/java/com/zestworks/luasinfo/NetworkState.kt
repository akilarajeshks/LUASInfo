package com.zestworks.luasinfo

sealed class NetworkState<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkState<T>()
    data class Error(val reason: String) : NetworkState<Nothing>()
}
