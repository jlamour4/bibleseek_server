package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.Verse
import org.springframework.data.jpa.repository.JpaRepository

interface VersesRepository : JpaRepository<Verse, Long> {
    fun findAllByTopicId(topicId: Int): List<Verse>
}