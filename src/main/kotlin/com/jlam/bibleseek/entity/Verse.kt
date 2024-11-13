package com.jlam.bibleseek.entity
import jakarta.persistence.*

@Entity
@Table(name = "verses")
data class Verse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    val topic: Topic,

    @Column(name = "start_verse_id")
    val startVerseId: String,

    @Column(name = "end_verse_id")
    val endVerseId: String?,

    @Column(name = "vote_count")
    val voteCount: Int,

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    val createdBy: User
)