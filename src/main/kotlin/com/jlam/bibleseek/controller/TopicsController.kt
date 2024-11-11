package com.jlam.bibleseek.controller

import com.jlam.bibleseek.entity.Topics
import com.jlam.bibleseek.repository.TopicsRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/topics")
class TopicsController(val topicsRepository: TopicsRepository) {

    @GetMapping
    fun getAllTopics(): List<Topics> = topicsRepository.findAll()

    @GetMapping("/{id}")
    fun getTopicById(@PathVariable id: Long): ResponseEntity<Topics> =
        topicsRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createTopic(@RequestBody topic: Topics): Topics = topicsRepository.save(topic)

    @PutMapping("/{id}")
    fun updateTopic(@PathVariable id: Long, @RequestBody newTopic: Topics): ResponseEntity<Topics> {
        return topicsRepository.findById(id).map { existingTopic ->
            val updatedTopic = existingTopic.copy(
                name = newTopic.name,
                description = newTopic.description
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
