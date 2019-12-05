package com.zestworks.luasinfo.listing

data class ListingViewData(
    val stopName: String,
    var trams: List<TramItem>,
    val time: String
)

data class TramItem(
    val destination: String,
    val dueMins: String,
    val isDue: Boolean
)

enum class Stops {
    MAR,
    STI
}