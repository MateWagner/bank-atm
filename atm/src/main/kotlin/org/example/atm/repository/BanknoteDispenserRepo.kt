package org.example.atm.repository

import org.example.atm.entity.BanknoteDispenser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BanknoteDispenserRepo: JpaRepository<BanknoteDispenser, Long> {
}