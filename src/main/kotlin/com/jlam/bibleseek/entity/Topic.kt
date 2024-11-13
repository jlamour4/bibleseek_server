package com.jlam.bibleseek.entity
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "topics")
data class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "name", unique = true)
    val name: String,

    @Column(name = "tags")
    val tags: String?, // Assuming tags is a JSON or a text list

    @Column(name = "created_at", updatable = false)
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false)
    val createdBy: User? = null // Assuming there's a User entity for the creator

) {
}