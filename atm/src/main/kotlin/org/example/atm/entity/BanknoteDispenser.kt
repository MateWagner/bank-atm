package org.example.atm.entity

import jakarta.persistence.*

@Entity
data class BanknoteDispenser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long=0,
    @OneToMany
    var mutableSet: MutableSet<BanknoteTray>
)
