package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Reply
import com.example.positivesns.repository.ReplyRepository
import com.example.positivesns.response.post.PostListResponse
import org.springframework.stereotype.Service

@Service
class ReplyServiceImpl(
    private val datetimeService: DatetimeService,
    private val replyRepository: ReplyRepository,
    private val uuidService: UuidService,
) : ReplyService {
    override fun insertReply(
        replyId: String,
        text: String
    ) {
        val reply = createReply(replyId, text)
        replyRepository.insertReply(reply)
    }

    override fun getReplies(postId: String): List<PostListResponse> {
        TODO("Not yet implemented")
    }

    override fun deleteReply(replyId: String) {
        TODO("Not yet implemented")
    }

    override fun updateReply(replyId: String, text: String) {
        TODO("Not yet implemented")
    }

    fun createReply(
        postId: String,
        text: String,
    ): Reply {
        val datetime = datetimeService.getCurrentTime()
        return Reply(
            replyId = uuidService.getUuid().replace("-", ""),
            postId = postId,
            text = text,
            registeredTime = datetime.toInstant().toEpochMilli(),
            updatedTime = datetime.toInstant().toEpochMilli(),
        )
    }
}
