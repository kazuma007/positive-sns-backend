package com.example.positivesns.service

import com.example.positivesns.response.reply.ReplyResponse

interface ReplyService {
    fun insertReply(
        replyId: String,
        text: String,
    )

    fun getReplies(
        postId: String,
    ): List<ReplyResponse>

    fun deleteReply(
        replyId: String,
    )

    fun updateReply(
        replyId: String,
        text: String,
    )
}
