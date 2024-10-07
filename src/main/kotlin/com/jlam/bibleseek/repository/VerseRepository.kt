package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.Verse
import org.springframework.data.jpa.repository.JpaRepository

interface VerseRepository : JpaRepository<Verse, Long>