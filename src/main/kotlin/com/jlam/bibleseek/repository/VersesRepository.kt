package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.Verses
import org.springframework.data.jpa.repository.JpaRepository

interface VersesRepository : JpaRepository<Verses, Long>