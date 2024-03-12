package org.example.atm.repository

import org.example.atm.entity.BanknoteTray
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BanknoteTrayRepo: JpaRepository<BanknoteTray, Long> {
    fun getAllByIsReceiveIsTrue():List<BanknoteTray>
//    fun getAllByReceiveIsTrue()

    @Query(value = "SELECT SUM( tray.amount * tray.value) as available FROM BanknoteTray as tray WHERE tray.amount > 0 AND tray.isReceive = FALSE")
    fun getTotalAvailableAmount():Int
}
