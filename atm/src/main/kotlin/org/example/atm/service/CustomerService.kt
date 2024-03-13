package org.example.atm.service

import org.example.atm.api.BankApi
import org.example.atm.config.Roles
import org.example.atm.config.UserDetailsImp
import org.example.atm.entity.Customer
import org.example.atm.repository.CustomerRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bankApi: BankApi,
) {

    fun findByUsername(userName: String?): UserDetails {
        val consumer: Customer = customerRepository.getCustomerByUsername(userName)
            ?: throw UsernameNotFoundException("Custom implementation needed to load user by username.")
        val roles: MutableCollection<GrantedAuthority> = convertRoles(consumer.role)
        return customerToUserDetails(consumer, roles)
    }

    fun userBalance(userName: String): Int =
        bankApi.sendBalanceRequest(userName)

    fun modifyUserBalance(amount:Int, userName: String) =
        bankApi.modifyUserBalance(amount, userName)

    private fun convertRoles(role: Roles): MutableCollection<GrantedAuthority> =
        mutableSetOf(SimpleGrantedAuthority(role.name))

    private fun customerToUserDetails(costumer: Customer, roles: MutableCollection<GrantedAuthority>): UserDetails =
        UserDetailsImp(costumer.password, costumer.username, roles)
}




