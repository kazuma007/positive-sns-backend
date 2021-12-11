package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Reply
import com.example.positivesns.repository.ReplyRepository
import com.example.positivesns.response.post.ReplyListResponse
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.ZoneId
import java.time.ZonedDateTime

@ExtendWith(MockKExtension::class)
internal class ReplyServiceImplTest {

    @InjectMockKs
    private lateinit var replyServiceImpl: ReplyServiceImpl

    @MockK
    private lateinit var datetimeService: DatetimeService

    @MockK
    private lateinit var replyRepository: ReplyRepository

    @MockK
    private lateinit var uuidService: UuidService

    @Test
    fun createReply() {
        every {
            datetimeService.getCurrentTime()
        } returns ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo"))

        every {
            uuidService.getUuid()
        } returns "41b28baf93374cf5849b4076c56783dd"

        val actual = replyServiceImpl.createReply("5849b4076c56783dd41b28baf93374cf", "sample test")

        val expected = Reply(
            replyId = "41b28baf93374cf5849b4076c56783dd",
            postId = "5849b4076c56783dd41b28baf93374cf",
            text = "sample test",
            registeredTime = 1639170320000,
            updatedTime = 1639170320000,
        )

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { datetimeService.getCurrentTime() }
        verify(exactly = 1) { uuidService.getUuid() }
        confirmVerified(datetimeService)
        confirmVerified(uuidService)
    }

    @Test
    fun getReplies() {
        every {
            replyRepository.getReplies("41b28baf93374cf5849b4076c56783dd")
        } returns listOf(
            Reply(
                replyId = "41b28baf93374cf5849b4076c56783dd",
                postId = "5849b4076c56783dd41b28baf93374cf",
                text = "sample test",
                registeredTime = 1639170330000,
                updatedTime = 1639170330000,
            ),
            Reply(
                replyId = "28baf93374cf5849b4076c56783dd41b",
                postId = "5849b4076c56783dd41b28baf93374cf",
                text = "sample test",
                registeredTime = 1639170320000,
                updatedTime = 1639170320000,
            ),
        )

        val expected = listOf(
            ReplyListResponse(
                replyId = "41b28baf93374cf5849b4076c56783dd",
                text = "sample test",
                postId = "5849b4076c56783dd41b28baf93374cf",
                registeredTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 30, 0, ZoneId.of("Asia/Tokyo")),
                updatedTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 30, 0, ZoneId.of("Asia/Tokyo")),
            ),
            ReplyListResponse(
                replyId = "28baf93374cf5849b4076c56783dd41b",
                text = "sample test",
                postId = "5849b4076c56783dd41b28baf93374cf",
                registeredTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
                updatedTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
            ),
        )

        val actual = replyServiceImpl.getReplies("41b28baf93374cf5849b4076c56783dd")

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { replyRepository.getReplies("41b28baf93374cf5849b4076c56783dd") }
        confirmVerified(replyRepository)
    }
}
