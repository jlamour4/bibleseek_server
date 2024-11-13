package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsersRepository : JpaRepository<User, Long> {
    // Find a user by email or authProviderId (adjust based on your needs)
    fun findByEmail(email: String): Optional<User>
    fun findByAuthProviderId(authProviderId: String): Optional<User>
}
