package com.jlam.bibleseek

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import com.jlam.bibleseek.service.TopicDataSyncService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableScheduling
class BibleseekApplication {

//	@Bean
//	fun testTopicVoteUpdater(topicDataSyncService: TopicDataSyncService): CommandLineRunner {
//		return CommandLineRunner {
//			topicDataSyncService.updateVotesAndTopics() // Manually trigger the update for testing
//		}
//	}
}
fun main(args: Array<String>) {
	runApplication<BibleseekApplication>(*args)
}
