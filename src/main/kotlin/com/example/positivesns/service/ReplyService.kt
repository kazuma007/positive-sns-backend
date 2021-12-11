package com.example.positivesns.service

import com.example.positivesns.response.reply.ReplyListResponse

interface ReplyService {
    fun insertReply(
        replyId: String,
        text: String,
    )

    fun getReplies(
        postId: String,
    ): List<ReplyListResponse>

    fun deleteReply(
        replyId: String,
    )

    fun updateReply(
        replyId: String,
        text: String,
    )
}
