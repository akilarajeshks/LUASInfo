package com.zestworks.luasinfo

 val marStopInfo = StopInfo(
    stop = LUASInfoViewModel.Stops.MAR.name,
    created = "",
    message = "",
    stopAbv = "mar",
    direction =
    listOf(
        Direction(name = "Outbound",
            tram = Tram(
                destination = " ",
                dueMins = " "
            ))
    ))

 val stiStopInfo = StopInfo(
    stop = LUASInfoViewModel.Stops.STI.name,
    created = "",
    message = "",
    stopAbv = "sti",
    direction =
    listOf(
        Direction(name = "Inbound",
            tram = Tram(
                destination = " ",
                dueMins = " "
            ))
    )
)
