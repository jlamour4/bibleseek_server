package com.jlam.bibleseek.repository

import com.jlam.bibleseek.entity.Topics
import org.springframework.data.jpa.repository.JpaRepository

interface TopicsRepository : JpaRepository<Topics, Long>
