package kerporation.sarafan.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
        @Id
        var id: String? = null,
        var name: String? = null,
        var userpic: String? = null,
        var email: String? = null,
        var locale: String? = null,
        var lastVisit: LocalDateTime? = null
)
