package com.zestworks.luasinfo.common

sealed class ViewState<out T : Any> {
    data class Content<out T : Any>(val viewData: T) : ViewState<T>()
    data class Error(val reason: String) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}