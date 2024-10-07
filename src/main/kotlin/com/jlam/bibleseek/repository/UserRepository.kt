package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
