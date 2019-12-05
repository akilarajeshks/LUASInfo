package com.zestworks.luasinfo.listing

data class ListingViewData(
    val stopName: String,
    var trams: List<Tram>,
    val time: String
)

enum class Stops {
    MAR,
    STI
}