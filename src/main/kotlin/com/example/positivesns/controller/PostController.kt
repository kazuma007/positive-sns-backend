package com.example.positivesns.controller

import com.example.positivesns.form.InsertPostForm
import com.example.positivesns.response.post.InsertPostResponse
import com.example.positivesns.service.PostService
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["v1/post"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class PostController(
    private val postService: PostService
) {
    // TODO PostMappingに変更する
    @GetMapping
    fun insertPost(
        @RequestParam("userId", required = true)
        userId: String,

        @RequestParam("text", required = true)
        text: String,
    ): InsertPostResponse {
        postService.insertPost(userId, text)
        return InsertPostResponse(result = "success")
    }
}