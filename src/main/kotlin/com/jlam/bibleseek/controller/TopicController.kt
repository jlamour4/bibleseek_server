package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.Topic
import com.jlam.bibleseek.repository.TopicRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/topics")
class TopicController(val topicRepository: TopicRepository) {

    @GetMapping
    fun getAllTopics(): List<Topic> = topicRepository.findAll()

    @GetMapping("/{id}")
    fun getTopicById(@PathVariable id: Long): ResponseEntity<Topic> =
        topicRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createTopic(@RequestBody topic: Topic): Topic = topicRepository.save(topic)

    @PutMapping("/{id}")
    fun updateTopic(@PathVariable id: Long, @RequestBody newTopic: Topic): ResponseEntity<Topic> {
        return topicRepository.findById(id).map { existingTopic ->
            val updatedTopic = existingTopic.copy(
                name = newTopic.name,
                description = newTopic.description
            )
            ResponseEntity.ok(topicRepository.save(updatedTopic))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteTopic(@PathVariable id: Long): ResponseEntity<Void> {
        return topicRepository.findById(id).map {
            topicRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}
