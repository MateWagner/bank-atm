package org.example.atm.repository

import org.example.atm.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Int> {
    fun getCustomerByUsername(username: String?): Customer?
}