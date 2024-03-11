package org.example.atm.dev

import org.example.atm.config.Roles
import org.example.atm.data.CurrencyTypes
import org.example.atm.entity.BanknoteDispenser
import org.example.atm.entity.BanknoteTray
import org.example.atm.entity.Customer
import org.example.atm.repository.BanknoteDispenserRepo
import org.example.atm.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class DBInit(
    private val customerRepository: CustomerRepository,
    private val banknoteDispenserRepo: BanknoteDispenserRepo,
    @Value("\${atm.admin.username}")
    val adminUsername: String,
    @Value("\${atm.admin.password}")
    val adminPassword: String,
    @Value("\${atm.admin.currency}")
    val atmCurrencyType: String,
) : CommandLineRunner {


    override fun run(vararg args: String?) {
        customerRepository.deleteAll();
        banknoteDispenserRepo.deleteAll()

        val admin = Customer(
            username = adminUsername,
            password = adminPassword,
            role = Roles.ADMIN
        )
        customerRepository.save(admin)
        for (i: Int in 1..10) {
            val user = Customer(
                username = "user$i",
                password = "user$i",
                role = Roles.CUSTOMER
            )
            customerRepository.save(user)
        }

        val banknoteDispenser: BanknoteDispenser =
            BanknoteDispenser()


        for (trayValue in CurrencyTypes.valueOf(atmCurrencyType).trays) {
            val banknoteTray: BanknoteTray = BanknoteTray(
                isReceive = false,
                size = 50,
                value = trayValue,
                amount = 50,
            )
            banknoteDispenser.banknoteTrays.add(banknoteTray)
        }
        banknoteDispenserRepo.save(banknoteDispenser)

    }

}
