package kerporation.sarafan.repo

import kerporation.sarafan.domain.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepo : JpaRepository<Message, Long>
