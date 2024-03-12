package org.example.atm.controller

import org.example.atm.controller.dto.*
import org.example.atm.service.AtmService
import org.example.atm.service.CustomerService
import org.springframework.http.HttpStatus
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun refill(@RequestBody refillDTO: RefillDTO) =
        atmService.refill(refillDTO)
}
