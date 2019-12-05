package com.zestworks.luasinfo.listing

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml
data class Direction(
    @Attribute
    var name: String = "",
    @Element
    var tram: List<Tram>
)

@Xml
data class Tram(
    @Attribute
    var destination: String = "",
    @Attribute
    var dueMins: String = ""
)

@Xml
data class StopInfo(
    @Attribute
    var stop: String = "",
    @Attribute
    var created: String = "",
    @PropertyElement
    var message: String = "",
    @Attribute
    var stopAbv: String = "",
    @Element
    var direction: List<Direction>
)