package org.example.atm.service

import org.example.atm.api.BankApi
import org.example.atm.controller.dto.DepositDTO
import org.example.atm.controller.dto.RefillDTO
import org.example.atm.controller.dto.WithdrawDTO
import org.example.atm.data.CurrencyTypes
import org.example.atm.entity.BanknoteTray
import org.example.atm.repository.BanknoteDispenserRepo
import org.example.atm.repository.BanknoteTrayRepo
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class AtmService(
    @Value("\${atm.admin.currency}")
    val atmCurrencyType: String,
    val banknoteTrayRepo: BanknoteTrayRepo,
    val customerService: CustomerService,
) {

    fun refill(refillDTO: RefillDTO) {
        TODO()
    }
    fun deposit(depositDTO: DepositDTO, userName: String) {
        println(userName)
    }

    fun withdraw(withdrawDTO: WithdrawDTO, userName: String): Int {
        val amount:Int = withdrawDTO.amount

        val rule:Regex = CurrencyTypes.valueOf(atmCurrencyType).currencyBanknoteRule
        if (!rule.matches(amount.toString()))
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad amount")

        val atmMaxAvailable: Int = getMaxAvailableAmount()
        if (atmMaxAvailable < amount)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "ATM Don't have enough money")

        val customerMaxAvailable: Int = customerService.userBalance(userName)
        if (customerMaxAvailable < amount)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Customer balance is low")

        revokeAmountFromTrays(amount)
        customerService.modifyUserBalance(-amount, userName)

        return amount
    }

    private fun getMaxAvailableAmount(): Int { // TODO convert to sql request
        val banknoteTrays: MutableList<BanknoteTray> = banknoteTrayRepo.findAll()
        val availableAmount: Int = banknoteTrays.filter { it.amount > 0 }.sumOf { it.value * it.amount }
        return availableAmount
    }

    private fun revokeAmountFromTrays(amount: Int) {
        val banknoteTrays: MutableList<BanknoteTray> = banknoteTrayRepo.findAll()
        banknoteTrays.sortByDescending { it.value }
        var remaining = amount
        for (banknoteTray in banknoteTrays) {
            val count = remaining / banknoteTray.value
            if (count > 0) {
                val maxBanknoteCount: Int = banknoteTray.amount
                if (maxBanknoteCount > count) {
                    remaining -= count * banknoteTray.value
                    banknoteTray.amount -= count
                } else {
                    remaining -= maxBanknoteCount * banknoteTray.value
                    banknoteTray.amount = 0
                }
            }
            if (remaining == 0)
                break
        }
        banknoteTrayRepo.saveAll(banknoteTrays)
    }
}
