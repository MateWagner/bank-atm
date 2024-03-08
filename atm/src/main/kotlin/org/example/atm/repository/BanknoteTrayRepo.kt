package org.example.atm.repository

import org.example.atm.entity.BanknoteTray
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BanknoteTrayRepo: JpaRepository<BanknoteTray, Long> {
}