package com.example.positivesns.service

import com.example.positivesns.model.dynamo.Post
import com.example.positivesns.repository.PostRepository
import com.example.positivesns.response.post.PostResponse
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
internal class PostServiceImplTest {

    @InjectMockKs
    private lateinit var postService: PostServiceImpl

    @MockK
    private lateinit var datetimeService: DatetimeService

    @MockK
    private lateinit var postRepository: PostRepository

    @MockK
    private lateinit var uuidService: UuidService

    @Test
    fun createPost() {
        every {
            datetimeService.getCurrentTime()
        } returns ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo"))

        every {
            uuidService.getUuid()
        } returns "41b28baf93374cf5849b4076c56783dd"

        val actual = postService.createPost("test-user-1", "sample test")

        val expected = Post(
            userId = "test-user-1",
            text = "sample test",
            postId = "41b28baf93374cf5849b4076c56783dd",
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
    fun getPosts_withUserId() {
        every {
            postRepository.getPosts("sample-user")
        } returns listOf(
            Post(
                userId = "sample-user",
                text = "sample test 1",
                postId = "41b28baf93374cf5849b4076c56783dd",
                registeredTime = 1639170320000,
                updatedTime = 1639170320000,
            ),
            Post(
                userId = "sample-user",
                text = "sample test 2",
                postId = "5849b4076c56783dd41b28baf93374cf",
                registeredTime = 1639170330000,
                updatedTime = 1639170330000,
            ),
        )

        val expected = listOf(
            PostResponse(
                userId = "sample-user",
                text = "sample test 2",
                postId = "5849b4076c56783dd41b28baf93374cf",
                registeredTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 30, 0, ZoneId.of("Asia/Tokyo")),
                updatedTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 30, 0, ZoneId.of("Asia/Tokyo")),
            ),
            PostResponse(
                userId = "sample-user",
                text = "sample test 1",
                postId = "41b28baf93374cf5849b4076c56783dd",
                registeredTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
                updatedTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
            ),
        )

        val actual = postService.getPosts("sample-user")

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { postRepository.getPosts("sample-user") }
        confirmVerified(postRepository)
    }

    @Test
    fun getPosts_withoutUserId() {
        every {
            postRepository.getPosts()
        } returns listOf(
            Post(
                userId = "sample-user-1",
                text = "sample test 1",
                postId = "41b28baf93374cf5849b4076c56783dd",
                registeredTime = 1639170320000,
                updatedTime = 1639170320000,
            ),
            Post(
                userId = "sample-user-2",
                text = "sample test 2",
                postId = "5849b4076c56783dd41b28baf93374cf",
                registeredTime = 1639170330000,
                updatedTime = 1639170330000,
            ),
        )

        val expected = listOf(
            PostResponse(
                userId = "sample-user-2",
                text = "sample test 2",
                postId = "5849b4076c56783dd41b28baf93374cf",
                registeredTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 30, 0, ZoneId.of("Asia/Tokyo")),
                updatedTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 30, 0, ZoneId.of("Asia/Tokyo")),
            ),
            PostResponse(
                userId = "sample-user-1",
                text = "sample test 1",
                postId = "41b28baf93374cf5849b4076c56783dd",
                registeredTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
                updatedTime = ZonedDateTime.of(2021, 12, 11, 6, 5, 20, 0, ZoneId.of("Asia/Tokyo")),
            ),
        )

        val userId = null
        val actual = postService.getPosts(userId)

        Assertions.assertEquals(expected, actual)

        verify(exactly = 1) { postRepository.getPosts() }
        confirmVerified(postRepository)
    }
}
