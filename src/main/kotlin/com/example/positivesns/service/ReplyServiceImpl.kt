package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Reply
import com.example.positivesns.repository.ReplyRepository
import com.example.positivesns.response.post.ReplyListResponse
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override fun getReplies(postId: String): List<ReplyListResponse> {
        val replies = replyRepository.getReplies(postId)

        if (replies.isEmpty()) {
            return emptyList()
        }

        return replies.map { reply ->
            ReplyListResponse(
                postId = reply.postId,
                replyId = reply.replyId,
                text = reply.text,
                registeredTime = reply.registeredTime?.let {
                    ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(it),
                        ZoneId.systemDefault()
                    )
                },
                updatedTime = reply.updatedTime?.let {
                    ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(it),
                        ZoneId.systemDefault()
                    )
                },
            )
        }.sortedByDescending { it.registeredTime }
    }

    override fun deleteReply(replyId: String) {
        val reply = Reply(
            replyId = replyId,
        )
        replyRepository.deleteReply(reply)
    }

    override fun updateReply(replyId: String, text: String) {
        val reply = Reply(
            replyId = replyId,
            text = text,
        )
        replyRepository.updateReply(reply)
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
