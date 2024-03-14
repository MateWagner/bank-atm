package org.example.bank.service

import org.example.bank.controller.dto.BalanceResponseDTO
import org.example.bank.controller.dto.ModifyBalanceDTO
import org.example.bank.entity.Customer
import org.example.bank.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {
    fun userBalance(userName:String): BalanceResponseDTO {
        val customer = getUserByUsername(userName)
        return BalanceResponseDTO(userName, customer.balance)
    }

    fun modifyBalance(modifyBalanceDTO: ModifyBalanceDTO) {
        val customer = getUserByUsername(modifyBalanceDTO.userName)
        customer.balance += modifyBalanceDTO.amount
        customerRepository.save(customer)
    }

    private fun getUserByUsername(userName: String):Customer =
        customerRepository.findByUsername(userName)
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "Can't find customer with username: $userName")

}
