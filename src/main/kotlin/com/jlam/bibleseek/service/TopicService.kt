package com.jlam.bibleseek.service

import com.jlam.bibleseek.entity.Verse
import com.jlam.bibleseek.repository.VersesRepository
import org.springframework.stereotype.Service

@Service
class TopicService(val versesRepository: VersesRepository) {

    fun getVersesByTopicId(topicId: Int): List<Verse> {
        return versesRepository.findAllByTopicId(topicId)
    }
}