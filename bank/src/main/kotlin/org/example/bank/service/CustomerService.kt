package org.example.bank.service

import org.example.bank.controller.dto.BalanceRequestDTO
import org.example.bank.controller.dto.BalanceResponseDTO
import org.example.bank.entity.Customer
import org.example.bank.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {
    fun modifyBalance(balanceRequestDTO: BalanceRequestDTO): BalanceResponseDTO {
        TODO("Not yet implemented")
    }

}