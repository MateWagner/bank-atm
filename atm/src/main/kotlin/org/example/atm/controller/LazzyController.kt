package org.example.atm.controller

import org.example.atm.controller.dto.DepositDTO
import org.example.atm.controller.dto.RefillDTO
import org.example.atm.controller.dto.UserBalanceDTO
import org.example.atm.controller.dto.WithdrawDTO
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
    val customerService: CustomerService
) {

    @GetMapping("user-balance")
    fun userBalance( @AuthenticationPrincipal userDetails: UserDetails):UserBalanceDTO =
        customerService.userBalance(userDetails.username)
    @PostMapping("/withdraw")
    fun withdraw(
        @RequestBody withdrawDTO: WithdrawDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ) =
        customerService.withdraw(withdrawDTO, userDetails.username)
    @PostMapping("/deposit")
    fun deposit(
        @RequestBody depositDTO: DepositDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ) =
        customerService.deposit(depositDTO, userDetails.username)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun refill(@RequestBody refillDTO: RefillDTO) =
        customerService.refill(refillDTO)
}