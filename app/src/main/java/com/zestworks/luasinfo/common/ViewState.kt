package com.zestworks.luasinfo.common

import com.zestworks.luasinfo.listing.ListingViewData

sealed class ViewState<out T : Any> {
    data class Content<out T : Any>(val listingViewData: ListingViewData) : ViewState<T>()
    data class Error(val reason: String) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}