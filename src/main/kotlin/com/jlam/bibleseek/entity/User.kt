package com.jlam.bibleseek.entity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

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