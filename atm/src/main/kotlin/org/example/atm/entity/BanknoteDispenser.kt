package org.example.atm.entity

import jakarta.persistence.*
import java.util.logging.Level.ALL

@Entity
@Table(name = "bn_dispenser")
data class BanknoteDispenser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "bn_dispenser_id")
    var banknoteTrays: MutableSet<BanknoteTray> = hashSetOf()
)
