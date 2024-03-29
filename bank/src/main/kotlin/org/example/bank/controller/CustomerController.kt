package org.example.bank.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.models.media.StringSchema
import org.example.bank.controller.dto.BalanceResponseDTO
import org.example.bank.controller.dto.ModifyBalanceDTO
import org.example.bank.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
class CustomerController(
    val customerService: CustomerService
) {
    @GetMapping("/user-balance/{userName}")
    fun userBalance(@PathVariable userName: String): BalanceResponseDTO =
        customerService.userBalance(userName)
    @PostMapping("/modify-balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun modifyBalance(@RequestBody modifyBalanceDTO: ModifyBalanceDTO) =
        customerService.modifyBalance(modifyBalanceDTO)
}
