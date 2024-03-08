package org.example.atm.service

import org.example.atm.repository.BanknoteDispenserRepo
import org.springframework.stereotype.Service

@Service
class AtmController(
    val banknoteDispenserRepo: BanknoteDispenserRepo
) {

    fun isAmountAvailable(int: Int): Boolean = TODO()
    fun withdraw(int: Int): Int = TODO()
    fun deposit(int: Int){TODO()}
}