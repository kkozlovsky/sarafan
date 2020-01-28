package kerporation.sarafan.repo

import kerporation.sarafan.domain.Message
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepo : JpaRepository<Message, Long> {
    @EntityGraph(attributePaths = ["comments"])
    override fun findAll() : MutableList<Message>
}
