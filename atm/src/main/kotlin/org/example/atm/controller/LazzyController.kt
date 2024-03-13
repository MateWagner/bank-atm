package org.example.atm.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.atm.controller.dto.BanknoteMapDTO
import org.example.atm.controller.dto.WithdrawDTO
import org.example.atm.service.AtmService
import org.example.atm.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@Tag(name = "Main Controller", description = "It serves to collect all the endpoints in one place")
@RestController
@RequestMapping("/api/v1/")
class LazzyController(
    val customerService: CustomerService,
    val atmService: AtmService
) {
    @Operation(summary = "User can get the current balance")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = Int::class)
            )]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error Bank unavailable",
            content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(
                    example = """{
  "timestamp": "2024-03-13T08:26:19.800+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/api/v1/user-balance"
}"""
                )
            )]

        ),
        ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema())]),
    )
    @SecurityRequirement(name = "user_auth")
    @GetMapping("user-balance")
    fun userBalance(@AuthenticationPrincipal userDetails: UserDetails): Int =
        customerService.userBalance(userDetails.username)

    @Operation(
        summary = "User can withdraw money",
        description = "Based on the currency region and available banknotes the take out amount can change"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [Content(
                schema = Schema(implementation = BanknoteMapDTO::class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )]
        ),

        ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(
                        example = "{message: 400 ATM Don't have enough money}"
                    )
                ),
            ]
        ),
        ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema())]),
    )
    @SecurityRequirement(name = "user_auth")
    @PostMapping("/withdraw")
    fun withdraw(
        @RequestBody withdrawDTO: WithdrawDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ): BanknoteMapDTO =
        atmService.withdraw(withdrawDTO, userDetails.username)

    @Operation(summary = "User can deposit money")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK", content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = Int::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema())]),
    )
    @SecurityRequirement(name = "user_auth")
    @PostMapping("/deposit")
    fun deposit(
        @RequestBody banknoteMapDTO: BanknoteMapDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ) =
        atmService.deposit(banknoteMapDTO, userDetails.username)

    @SecurityRequirement(name = "admin_role")
    @Operation(
        summary = "Admin can fill up the ATM",
        description = "Admin account can fill up the ATM. Login credential need to include to the application.properties"
    )
    @ApiResponses(
        ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema())]),
        ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema())]),
    )
    @PostMapping("admin/fill")
    @ResponseStatus(HttpStatus.CREATED)
    fun refill(@RequestBody banknoteMapDTO: BanknoteMapDTO): BanknoteMapDTO =
        atmService.refill(banknoteMapDTO)
}
