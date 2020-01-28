package kerporation.sarafan.controller

import kerporation.sarafan.domain.Comment
import kerporation.sarafan.domain.User
import kerporation.sarafan.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("comment")
class CommentController(
        @Autowired private val commentService: CommentService
) {
    @PostMapping
    fun create(@RequestBody  comment: Comment, @AuthenticationPrincipal user: User) = commentService.create(comment, user)
}
