package com.example.positivesns.repository

import com.example.positivesns.model.dynamo.Reply

interface ReplyRepository {
    fun insertReply(reply: Reply)
    fun deleteReply(reply: Reply)
    fun updateReply(reply: Reply)
    fun getReplies(postId: String): List<Reply>
}
