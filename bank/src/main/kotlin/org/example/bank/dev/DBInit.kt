package org.example.bank.dev

import org.example.bank.entity.Customer
import org.example.bank.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class DBInit(
    private val customerRepository: CustomerRepository,
) : CommandLineRunner {


    override fun run(vararg args: String?) {
        customerRepository.deleteAll();
        for (i: Int in 1..10) {
            val user = Customer(
                username = "user$i",
                balance = 800000
            )
            customerRepository.save(user)
        }
    }

}