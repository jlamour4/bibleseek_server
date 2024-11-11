package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.Verses
import com.jlam.bibleseek.repository.VersesRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/verses")
class VersesController(val versesRepository: VersesRepository) {

    @GetMapping
    fun getAllVerses(): List<Verses> = versesRepository.findAll()

    @GetMapping("/{id}")
    fun getVerseById(@PathVariable id: Long): ResponseEntity<Verses> =
        versesRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createVerse(@RequestBody verse: Verses): Verses = versesRepository.save(verse)

    @PutMapping("/{id}")
    fun updateVerse(@PathVariable id: Long, @RequestBody newVerse: Verses): ResponseEntity<Verses> {
        return versesRepository.findById(id).map { existingVerse ->
            val updatedVerse = existingVerse.copy(
                verseText = newVerse.verseText,
                book = newVerse.book,
                chapter = newVerse.chapter,
                verseNumber = newVerse.verseNumber,
                voteCount = newVerse.voteCount
            )
            ResponseEntity.ok(versesRepository.save(updatedVerse))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteVerse(@PathVariable id: Long): ResponseEntity<Void> {
        return versesRepository.findById(id).map {
            versesRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}
