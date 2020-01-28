package kerporation.sarafan.domain

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id::class)
    var id: Long? = null

    @JsonView(Views.Id::class)
    var text: String? = null

    @ManyToOne
    @JoinColumn(name = "message_id")
    var message: Message? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(Views.FullMessage::class)
    var author: User? = null;

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}
