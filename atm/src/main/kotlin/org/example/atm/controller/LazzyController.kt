package org.example.atm.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jdk.jfr.ContentType
import org.example.atm.controller.dto.*
import org.example.atm.service.AtmService
import org.example.atm.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
class LazzyController(
    val customerService: CustomerService,
    val atmService: AtmService
) {


    @GetMapping("user-balance")
    fun userBalance( @AuthenticationPrincipal userDetails: UserDetails): Int =
        customerService.userBalance(userDetails.username)
    @PostMapping("/withdraw")
    fun withdraw(
        @RequestBody withdrawDTO: WithdrawDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ): BanknoteMapDTO =
        atmService.withdraw(withdrawDTO, userDetails.username)
    @PostMapping("/deposit")
    fun deposit(
        @RequestBody banknoteMapDTO: BanknoteMapDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ) =
        atmService.deposit(banknoteMapDTO, userDetails.username)
    @Operation(
        summary = "Admin can fill up the ATM",
        description = "Admin account can fill up the ATM. Login credential need to include to the application.properties"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Successful Operation",
                content = [
                    Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                ]
                ),
        ]
    )
    @PostMapping("admin/fill")
    @ResponseStatus(HttpStatus.CREATED)
    fun refill(@RequestBody banknoteMapDTO: BanknoteMapDTO):BanknoteMapDTO =
        atmService.refill(banknoteMapDTO)
}
