package com.example.positivesns.controller

import com.example.positivesns.response.post.PostListResponse
import com.example.positivesns.response.post.PostResultResponse
import com.example.positivesns.service.ReplyService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["v1/reply"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class ReplyController(
    private val replyService: ReplyService
) {
    // TODO PostMappingに変更する
    @GetMapping(
        path = ["insert"]
    )
    fun insertPost(
        @RequestParam("postId")
        postId: String,

        @RequestParam("text")
        text: String,
    ): PostResultResponse {
        replyService.insertReply(postId, text)
        return PostResultResponse(result = "success")
    }

    @GetMapping(
        path = ["get"]
    )
    fun getPosts(
        @RequestParam("postId")
        postId: String,
    ): List<PostListResponse> {
        return replyService.getReplies(postId)
    }

    @GetMapping(
        path = ["delete"]
    )
    fun deletePost(
        @RequestParam("postId")
        postId: String,
    ): PostResultResponse {
        replyService.deleteReply(postId)
        return PostResultResponse(result = "success")
    }

    @GetMapping(
        path = ["update"]
    )
    fun updatePost(
        @RequestParam("text")
        text: String,

        @RequestParam("postId")
        postId: String,
    ): PostResultResponse {
        replyService.updateReply(postId, text)
        return PostResultResponse(result = "success")
    }
}