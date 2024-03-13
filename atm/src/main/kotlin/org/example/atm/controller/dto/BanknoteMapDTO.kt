package org.example.atm.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Banknotes maps for request and response")
data class BanknoteMapDTO(
@field:Schema(
    description = "banknotes: Map",
    example = "HUF={500:1,1000:1,2000:1,5000:1,10000:1, 20000:1} EUR={5:1,10:1,20:1,50:1,100:1,200:1,500:1}",
    type = "Map",
)
    val banknotes:Map<Int,Int>
)
