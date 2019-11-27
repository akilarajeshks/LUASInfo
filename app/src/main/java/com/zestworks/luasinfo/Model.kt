package com.zestworks.luasinfo

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "stopInfo", strict = false)
data class StopInfo(
    @Attribute(required = false)
    val stop: String?,
    @Attribute(required = false)
    val created: String?,
    @Attribute(required = false)
    val message: String?,
    @Attribute(required = false)
    val stopAbv: String?,
    @ElementList
    val direction: List<Direction>
)

data class Direction(
    @Element(name = "name")
    val name: String,
    @Element(name = "tram")
    val tram: Tram
)

data class Tram(
    @Element(name = "destination")
    val destination: String?,
    @Element(name = "dueMins")
    val dueMins: String?
)