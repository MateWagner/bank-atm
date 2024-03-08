package org.example.atm.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import org.example.atm.config.Roles


@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long=0,
    @Column(name = "username", nullable = false, unique = true)
    var username: String,
    @Column(name = "password", nullable = false)
    var password: String,
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    var role: Roles,
)
