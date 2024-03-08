package org.example.bank.controller

import org.example.bank.controller.dto.BalanceRequestDTO
import org.example.bank.controller.dto.BalanceResponseDTO
import org.example.bank.entity.Customer
import org.example.bank.service.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
class CustomerController(
    val customerService: CustomerService
) {

    @GetMapping("/modify-balance")
    fun modifyBalance(@RequestBody balanceRequestDTO: BalanceRequestDTO): BalanceResponseDTO =
        customerService.modifyBalance(balanceRequestDTO)
}