package com.zestworks.luasinfo

import com.zestworks.luasinfo.listing.ViewData

sealed class ViewState<out T : Any> {
    data class Content<out T : Any>(val viewData: ViewData) : ViewState<T>()
    data class Error(val reason: String) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}