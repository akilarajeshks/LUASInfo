package com.zestworks.luasinfo.listing

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml
data class Direction(
    @Attribute
    val name: String,
    @Element
    val tram: List<Tram>
)

@Xml
data class Tram(
    @Attribute
    val destination: String,
    @Attribute
    val dueMins: String
)

@Xml
data class StopInfo(
    @Attribute
    val stop: String,
    @Attribute
    val created: String,
    @PropertyElement
    val message: String,
    @Attribute
    val stopAbv: String,
    @Element
    val direction: List<Direction>
) {
    fun getInbound(): List<Direction> = direction.filter {
        it.name == "Inbound"
    }

    fun getOutbound(): List<Direction> = direction.filter {
        it.name == "Outbound"
    }
}