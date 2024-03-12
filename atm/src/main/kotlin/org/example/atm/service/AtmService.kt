package org.example.atm.service

import org.example.atm.controller.dto.BanknoteMapDTO
import org.example.atm.controller.dto.RefillDTO
import org.example.atm.controller.dto.WithdrawDTO
import org.example.atm.data.CurrencyTypes
import org.example.atm.entity.BanknoteTray
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

    fun deposit(banknoteMapDTO: BanknoteMapDTO, userName: String): Int {
        val allowedBanknotes: Set<Int> = CurrencyTypes.valueOf(atmCurrencyType).trays
        if (!allowedBanknotes.containsAll(banknoteMapDTO.banknotes.keys))
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not conform banknote found")
        val receiverTrays: List<BanknoteTray> = banknoteTrayRepo.getAllByIsReceiveIsTrue()
        val maxAvailableSpace: Int = receiverTrays.sumOf { it.size - it.amount }
        val numberOfBanknote: Int = banknoteMapDTO.banknotes.values.sum()

        if (maxAvailableSpace < numberOfBanknote)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Don't have enough storage")

        storeBanknotes(banknoteMapDTO, receiverTrays)

        val amount = banknoteMapDTO.banknotes.entries.sumOf { it.key*it.value }
        customerService.modifyUserBalance(amount, userName)
        return amount
    }

    private fun storeBanknotes(
        banknoteMapDTO: BanknoteMapDTO,
        receiverTrays: List<BanknoteTray>
    ) {
        val remainingNotes: MutableList<Int> = banknoteMapDTO
            .banknotes
            .flatMap { (kay, value) -> List(value) { kay } }
            .toMutableList()

        for (receiverTray in receiverTrays) {
            if (receiverTray.amount == receiverTray.size)
                continue
            for (i in 0 until remainingNotes.size) {
                receiverTray.value += remainingNotes.removeFirst()
                receiverTray.amount++
                if (receiverTray.amount == receiverTray.size)
                    break
            }
        }
        banknoteTrayRepo.saveAll(receiverTrays)
    }

    fun withdraw(withdrawDTO: WithdrawDTO, userName: String): BanknoteMapDTO {
        val amount: Int = withdrawDTO.amount

        val rule: Regex = CurrencyTypes.valueOf(atmCurrencyType).currencyBanknoteRule
        if (!rule.matches(amount.toString()))
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad amount")

        val atmMaxAvailable: Int = banknoteTrayRepo.getTotalAvailableAmount()
        if (atmMaxAvailable < amount)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "ATM Don't have enough money")

        val customerMaxAvailable: Int = customerService.userBalance(userName)
        if (customerMaxAvailable < amount)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Customer balance is low")

        val banknoteMap: Map<Int, Int> = revokeAmountFromTrays(amount)
        customerService.modifyUserBalance(-amount, userName)

        return BanknoteMapDTO(banknoteMap)
    }

    private fun revokeAmountFromTrays(amount: Int): Map<Int, Int> {
        val resultMap: MutableMap<Int, Int> = mutableMapOf()
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
                    resultMap[banknoteTray.value] = count
                } else {
                    remaining -= maxBanknoteCount * banknoteTray.value
                    banknoteTray.amount = 0
                    resultMap[banknoteTray.value] = maxBanknoteCount
                }
            }
            if (remaining == 0)
                break
        }
        banknoteTrayRepo.saveAll(banknoteTrays)
        return resultMap
    }
}
