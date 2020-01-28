package kerporation.sarafan.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonView
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
        @Id
        @JsonView(Views.IdName::class)
        var id: String? = null,
        @JsonView(Views.IdName::class)
        var name: String? = null,
        @JsonView(Views.IdName::class)
        var userpic: String? = null,
        var email: String? = null,
        var locale: String? = null,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        var lastVisit: LocalDateTime? = null
) : Serializable
