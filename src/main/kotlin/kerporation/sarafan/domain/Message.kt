package kerporation.sarafan.domain

import javax.persistence.*

@Entity
@Table
data class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        var text: String? = null
)
