package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<Users, Long>
