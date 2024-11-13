package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.Verse
import com.jlam.bibleseek.repository.VersesRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/verses")
class VersesController(val versesRepository: VersesRepository) {

    @GetMapping
    fun getAllVerses(): List<Verse> = versesRepository.findAll()

    @GetMapping("/{id}")
    fun getVerseById(@PathVariable id: Long): ResponseEntity<Verse> =
        versesRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createVerse(@RequestBody verse: Verse): Verse = versesRepository.save(verse)

    @PutMapping("/{id}")
    fun updateVerse(@PathVariable id: Long, @RequestBody newVerse: Verse): ResponseEntity<Verse> {
        return versesRepository.findById(id).map { existingVerse ->
            val updatedVerse = existingVerse.copy(
                startVerseId = newVerse.startVerseId,
                endVerseId = newVerse.endVerseId,
                topic = newVerse.topic,
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
