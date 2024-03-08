package org.example.atm.service

import org.example.atm.config.Roles
import org.example.atm.config.UserDetailsImp
import org.example.atm.controller.dto.DepositDTO
import org.example.atm.controller.dto.RefillDTO
import org.example.atm.controller.dto.UserBalanceDTO
import org.example.atm.controller.dto.WithdrawDTO
import org.example.atm.entity.Customer
import org.example.atm.repository.CustomerRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {
    fun findByUsername(username: String?): UserDetails {
        val consumer: Customer = customerRepository.getCustomerByUsername(username)
            ?: throw UsernameNotFoundException("Custom implementation needed to load user by username.")
        val roles: MutableCollection<GrantedAuthority> = convertRoles(consumer.role)
        return customerToUserDetails(consumer, roles)
    }

    private fun convertRoles(role: Roles): MutableCollection<GrantedAuthority> =
        mutableSetOf(SimpleGrantedAuthority(role.name))


    fun customerToUserDetails(costumer: Customer, roles: MutableCollection<GrantedAuthority>): UserDetails =
        UserDetailsImp(costumer.password, costumer.username, roles)

    fun withdraw(withdrawDTO: WithdrawDTO, username: String) {
        println(username)
    }

    fun deposit(depositDTO: DepositDTO, username: String) {
        println(username)
    }
    fun userBalance(username: String): UserBalanceDTO =
        TODO()

    fun refill(refillDTO: RefillDTO) {
        TODO()
    }
}




