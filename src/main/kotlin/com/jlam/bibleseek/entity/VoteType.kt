package com.jlam.bibleseek.entity

enum class VoteType {
    UPVOTE {
        override fun applyVote(currentVotes: Int): Int {
            return currentVotes + 1 // Increment the vote count
        }
    },
    DOWNVOTE {
        override fun applyVote(currentVotes: Int): Int {
            return currentVotes - 1 // Decrement the vote count
        }
    };

    abstract fun applyVote(currentVotes: Int): Int
}