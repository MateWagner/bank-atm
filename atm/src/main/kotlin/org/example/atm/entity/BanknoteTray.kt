package org.example.atm.entity

import jakarta.persistence.*

@Entity
data class BanknoteTray(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long=0,
    var isReceive: Boolean,
    var value: Int,
    var size: Int,
    var amount: Int,
)
