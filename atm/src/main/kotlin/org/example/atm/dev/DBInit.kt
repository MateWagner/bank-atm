package org.example.atm.dev

import org.example.atm.config.Roles
import org.example.atm.entity.Customer
import org.example.atm.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class DBInit(
    private val customerRepository: CustomerRepository,
    @Value("\${atm.admin.username}")
    val adminUsername: String,
    @Value("\${atm.admin.password}")
    val adminPassword: String
) : CommandLineRunner {


    override fun run(vararg args: String?) {
        customerRepository.deleteAll();
        val admin = Customer(
            username = adminUsername,
            password = adminPassword,
            role = Roles.ADMIN
        )
        customerRepository.save(admin)
    }

}