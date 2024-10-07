package com.jlam.bibleseek.entity
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Verse.kt
@Entity
data class Verse(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val verseText: String,
    val book: String,
    val chapter: Int,
    val verseNumber: Int,
    var voteCount: Long = 0
)