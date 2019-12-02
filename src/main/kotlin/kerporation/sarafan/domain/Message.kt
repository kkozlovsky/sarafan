package kerporation.sarafan.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonView
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
data class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @JsonView(Views.Id::class)
        var id: Long? = null,
        @JsonView(Views.IdName::class)
        var text: String? = null
) {
        @Column(updatable = false)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonView(Views.FullMessage::class)
        val creationDate: LocalDateTime = LocalDateTime.now()
}
