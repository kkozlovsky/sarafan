package kerporation.sarafan.service

import kerporation.sarafan.domain.Comment
import kerporation.sarafan.domain.User
import kerporation.sarafan.repo.CommentRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService(
        @Autowired private val commentRepo: CommentRepo
) {

    fun create(comment: Comment, user: User) : Comment {
        comment.author = user
        val savedComment = commentRepo.save(comment)
        return savedComment
    }
}
