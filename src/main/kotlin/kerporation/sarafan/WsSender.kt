package kerporation.sarafan

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import kerporation.sarafan.dto.EventType
import kerporation.sarafan.dto.ObjectType
import kerporation.sarafan.dto.WsEventDto
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.util.function.BiConsumer

@Component
class WsSender(private val template: SimpMessagingTemplate,
               private val mapper: ObjectMapper) {

    fun <T> getSender(objectType: ObjectType, view: Class<*>): BiConsumer<EventType, T> {
        val writer: ObjectWriter = mapper
                .setConfig(mapper.serializationConfig)
                .writerWithView(view)

        return BiConsumer<EventType, T> { eventType: EventType, payload: T ->
            lateinit var  value: String
            value = try {
                writer.writeValueAsString(payload)
            } catch (e: JsonProcessingException) {
                throw RuntimeException(e)
            }
            template.convertAndSend(
                    "/topic/activity",
                    WsEventDto(objectType, eventType, value)
            )
        }
    }
}
