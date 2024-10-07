package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.Verse
import com.jlam.bibleseek.repository.VerseRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/verses")
class VerseController(val verseRepository: VerseRepository) {

    @GetMapping
    fun getAllVerses(): List<Verse> = verseRepository.findAll()

    @GetMapping("/{id}")
    fun getVerseById(@PathVariable id: Long): ResponseEntity<Verse> =
        verseRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createVerse(@RequestBody verse: Verse): Verse = verseRepository.save(verse)

    @PutMapping("/{id}")
    fun updateVerse(@PathVariable id: Long, @RequestBody newVerse: Verse): ResponseEntity<Verse> {
        return verseRepository.findById(id).map { existingVerse ->
            val updatedVerse = existingVerse.copy(
                verseText = newVerse.verseText,
                book = newVerse.book,
                chapter = newVerse.chapter,
                verseNumber = newVerse.verseNumber,
                voteCount = newVerse.voteCount
            )
            ResponseEntity.ok(verseRepository.save(updatedVerse))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteVerse(@PathVariable id: Long): ResponseEntity<Void> {
        return verseRepository.findById(id).map {
            verseRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}
