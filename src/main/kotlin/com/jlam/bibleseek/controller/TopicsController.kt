package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.Topic
import com.jlam.bibleseek.entity.Verse
import com.jlam.bibleseek.repository.TopicsRepository
import com.jlam.bibleseek.service.TopicService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/topics")
class TopicsController(
    val topicsRepository: TopicsRepository,
    val topicService: TopicService
) {

    @GetMapping
    fun getAllTopics(): List<Topic> = topicsRepository.findAll()

    @GetMapping("/{id}")
    fun getTopicById(@PathVariable id: Long): ResponseEntity<Topic> =
        topicsRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/{topicId}/verses")
    fun getVersesByTopicId(@PathVariable topicId: Int): ResponseEntity<List<Verse>> {
        val verses = topicService.getVersesByTopicId(topicId)  // Use service here
        return if (verses.isNotEmpty()) {
            ResponseEntity.ok(verses) // Return verses if found
        } else {
            ResponseEntity.notFound().build() // Return 404 if no verses are found
        }
    }

    @PostMapping
    fun createTopic(@RequestBody topic: Topic): Topic = topicsRepository.save(topic)

    @PutMapping("/{id}")
    fun updateTopic(@PathVariable id: Long, @RequestBody newTopic: Topic): ResponseEntity<Topic> {
        return topicsRepository.findById(id).map { existingTopic ->
            val updatedTopic = existingTopic.copy(
                name = newTopic.name,
                // Add any other fields you'd like to update here
            )
            ResponseEntity.ok(topicsRepository.save(updatedTopic))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteTopic(@PathVariable id: Long): ResponseEntity<Void> {
        return topicsRepository.findById(id).map {
            topicsRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}

