package com.example.positivesns.service

import com.example.positivesns.response.post.PostListResponse

interface ReplyService {
    fun insertReply(
        replyId: String,
        text: String,
    )

    fun getReplies(
        postId: String,
    ): List<PostListResponse>

    fun deleteReply(
        replyId: String,
    )

    fun updateReply(
        replyId: String,
        text: String,
    )
}