package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface TopicsRepository : JpaRepository<Topic, Long>
