package com.jlam.bibleseek.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "votes")
data class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "verse_id", referencedColumnName = "id")
    val verse: Verse,

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type")
    val voteType: VoteType?,

    @Column(name = "created_at")
    val createdAt: Timestamp
)