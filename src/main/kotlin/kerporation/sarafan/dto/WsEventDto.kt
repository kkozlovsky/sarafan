package kerporation.sarafan.dto

import com.fasterxml.jackson.annotation.JsonRawValue
import com.fasterxml.jackson.annotation.JsonView
import kerporation.sarafan.domain.Views

@JsonView(Views.Id::class)
data class WsEventDto(
        val objectType: ObjectType,
        val eventType: EventType,
        @JsonRawValue val body: String
) {
}
