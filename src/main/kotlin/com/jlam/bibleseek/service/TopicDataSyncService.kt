package com.jlam.bibleseek.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.File
import java.net.URL

@Service
class TopicDataSyncService( private val topicDataUpdaterService: TopicDataUpdaterService
) {

    private val dataUrl = "https://a.openbible.info/data/topic-votes.txt"
    private val tempFilePath = "./temp_topic_votes.txt"

    // Method to download the file
    private fun downloadFile(url: String, filePath: String) {
        URL(url).openStream().use { input ->
            File(filePath).outputStream().use { output ->
                input.copyTo(output)
            }
        }
        println("File downloaded successfully.")
    }

    // Method to parse the file into a JSON-like map
    private fun parseFile(filePath: String): Map<String, List<Map<String, String>>> {
        val jsonObject = mutableMapOf<String, MutableList<Map<String, String>>>()
        File(filePath).useLines { lines ->
            lines.drop(1).forEach { line ->
                val splitLine = line.split("\t")
                if (splitLine.size < 4) return@forEach

                val topic = splitLine[0]
                val startVerseId = splitLine[1]
                val endVerseId = splitLine[2]
                val votes = splitLine[3]

                if (topic.isNotEmpty()) {
                    jsonObject.computeIfAbsent(topic) { mutableListOf() }
                        .add(mapOf("startVerseId" to startVerseId, "endVerseId" to endVerseId, "votes" to votes))
                }
            }
        }
        println("File parsed successfully.")
        return jsonObject
    }

    // Run this task every week (e.g., every Monday at 3 AM)
    @Scheduled(cron = "0 0 3 * * MON")
    fun updateVotesAndTopics() {
        try {
                println("Starting weekly topic votes update...")
            downloadFile(dataUrl, tempFilePath)
            val jsonObject = parseFile(tempFilePath)
            topicDataUpdaterService.updateDatabase(jsonObject)
            println("Update complete!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}