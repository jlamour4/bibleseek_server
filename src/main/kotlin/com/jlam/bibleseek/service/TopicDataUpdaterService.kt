package com.jlam.bibleseek.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException

@Service
@Transactional
class TopicDataUpdaterService(@Autowired private val jdbcTemplate: JdbcTemplate) {
    fun updateDatabase(jsonObject: Map<String, List<Map<String, String>>>) {
        // Calculate the total number of verses to process
        val totalVerses = jsonObject.values.flatten().size
        var processedVerses = 0
        var lastPercentage = -1 // Start with a value that's less than 0 to ensure the first print happens

        jsonObject.forEach { (topic, verses) ->
            // Get or create the topic, and set created_by to the user ID
            val topicId = getOrCreateTopicId(topic)

            // Iterate over verses and add or update them
            verses.forEach { verse ->
                val startVerseCode = verse["startVerseId"] ?: ""
                val endVerseCode = verse["endVerseId"]?.takeIf { it.isNotEmpty() }
                val votes = verse["votes"]?.toIntOrNull() ?: 0

                // Ensure startVerseCode is valid before attempting to insert/update
                if (startVerseCode.isNotEmpty()) {
                    // Insert or update the verse with vote count
                    addOrUpdateVerse(topicId, startVerseCode, endVerseCode, votes)
                }

                // Increment the count of processed verses
                processedVerses++
            }

            // Calculate the percentage of completion after the topic is processed
            val percentageCompleted = (processedVerses * 100) / totalVerses

            // Only print if the percentage has increased
            if (percentageCompleted > lastPercentage) {
                println("Progress: $percentageCompleted% ($processedVerses of $totalVerses verses processed)")
                lastPercentage = percentageCompleted // Update the last percentage
            }

        }
        println("Database updated successfully.")
    }

    private fun getOrCreateTopicId(topicName: String): Int {
        val userId = 1 // Replace with your actual user ID if needed

        // Check if the topic already exists
        val query = "SELECT id FROM topics WHERE name = ?"
        val existingTopicId: Int? = try {
            jdbcTemplate.queryForObject(query, Int::class.java, topicName)
        } catch (e: EmptyResultDataAccessException) {
            null
        }

        if (existingTopicId != null) {
            return existingTopicId
        }

        // Insert the new topic if it doesn't exist
        val insertQuery = """
        INSERT INTO topics (name, created_by)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE name = VALUES(name)
    """
        jdbcTemplate.update(insertQuery, topicName, userId)

        // Retrieve the new or existing topic ID
        return jdbcTemplate.queryForObject(query, Int::class.java, topicName)
    }

    private fun addOrUpdateVerse(topicId: Int, startVerseCode: String, endVerseCode: String?, importedVoteCount: Int) {
        val userId = 1 // Replace with your actual user ID if needed
        // Insert or update the verse, ESV by default via OpenBible.info
        val insertQuery = """
        INSERT INTO verses (topic_id, start_verse_code, end_verse_code, translation, upvotes, downvotes, imported_votes, created_by)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE 
            imported_votes = VALUES(imported_votes)
        """
        jdbcTemplate.update(insertQuery, topicId, startVerseCode, endVerseCode, "ESV", 0, 0, importedVoteCount, userId)
    }
}