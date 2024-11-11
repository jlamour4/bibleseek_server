package com.jlam.bibleseek.entity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

// User.kt
@Entity
data class Users(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(name = "profile_picture_url")
    val profilePictureUrl: String? = null,

    @Column(name = "auth_provider", nullable = false)
    val authProvider: String,

    @Column(name = "auth_provider_id", nullable = false)
    val authProviderId: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)