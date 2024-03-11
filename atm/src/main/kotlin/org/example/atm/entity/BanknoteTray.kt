package org.example.atm.entity

import jakarta.persistence.*

@Entity
@Table(name = "bn_tray")
data class BanknoteTray(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long=0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bn_dispenser_id")
    var bnDispenser: BanknoteDispenser? = null,
    @Column(name = "is_receive", nullable = false)
    var isReceive: Boolean,
    @Column(name = "value", nullable = false)
    var value: Int,
    @Column(name = "size", nullable = false)
    var size: Int,
    @Column(name = "amount", nullable = false)
    var amount: Int,
)
