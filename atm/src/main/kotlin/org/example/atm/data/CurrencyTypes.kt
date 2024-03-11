package org.example.atm.data

enum class CurrencyTypes(val trays: Set<Int>, val currencyBanknoteRule:Regex) {
    EUR(setOf(5,10,20,50,100,200,500), Regex("[0-9]*[50]{1}00$")),
    HUF(setOf(500, 1000, 2000, 5000, 10000, 20000), Regex("[0-9]*[50]{1}00$"))
}
