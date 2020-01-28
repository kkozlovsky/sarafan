package kerporation.sarafan.repo

import kerporation.sarafan.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepo : JpaRepository<Comment, Long>
