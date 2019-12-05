package com.zestworks.luasinfo

import com.zestworks.luasinfo.extensions.isDigitsOnly
import com.zestworks.luasinfo.listing.*

val marStopInfo = StopInfo(
    stop = Stops.MAR.name,
    created = "",
    message = "",
    stopAbv = "mar",
    direction =
    listOf(
        Direction(
            name = "Outbound",
            tram = listOf(
                Tram(
                    destination = " ",
                    dueMins = " "
                ), Tram(
                    destination = " ",
                    dueMins = "Due"
                )
            )
        )
    )
)

 val stiStopInfo = StopInfo(
     stop = Stops.STI.name,
     created = "",
     message = "",
     stopAbv = "sti",
     direction =
     listOf(
         Direction(
             name = "Inbound",
             tram = listOf(
                 Tram(
                     destination = " ",
                     dueMins = "7"
                 ),
                 Tram(
                     destination = " ",
                     dueMins = "Due"
                 )
             )
         )
     )
 )

val outboundViewList = marStopInfo.getOutbound().flatMap { it.tram }.map {
    TramItem(
        destination = it.destination,
        dueMins = it.dueMins,
        isDue = it.dueMins.isDigitsOnly().not()
    )
}

val inboundViewList = marStopInfo.getInbound().flatMap { it.tram }.map {
    TramItem(
        destination = it.destination,
        dueMins = it.dueMins,
        isDue = it.dueMins.isDigitsOnly().not()
    )
}
