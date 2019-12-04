package com.zestworks.luasinfo

import com.zestworks.luasinfo.listing.Direction
import com.zestworks.luasinfo.listing.ListingViewModel
import com.zestworks.luasinfo.listing.StopInfo
import com.zestworks.luasinfo.listing.Tram

val marStopInfo = StopInfo(
    stop = ListingViewModel.Stops.MAR.name,
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
                )
            )
        )
    )
)

 val stiStopInfo = StopInfo(
     stop = ListingViewModel.Stops.STI.name,
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
                     dueMins = " "
                 )
             )
         )
     )
 )
