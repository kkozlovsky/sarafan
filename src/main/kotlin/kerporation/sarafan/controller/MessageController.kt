package kerporation.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import kerporation.sarafan.WsSender
import kerporation.sarafan.domain.Message
import kerporation.sarafan.domain.Views
import kerporation.sarafan.dto.EventType
import kerporation.sarafan.dto.ObjectType
import kerporation.sarafan.repo.MessageRepo
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.function.BiConsumer

@RestController
@RequestMapping("message")
class MessageController @Autowired constructor(
        private val messageRepo: MessageRepo,
        wsSenderInstance: WsSender
        ) {

    private val wsSender: BiConsumer<EventType, Message> = wsSenderInstance.getSender(ObjectType.MESSAGE, Views.Id::class.java)

    @GetMapping
    @JsonView(Views.IdName::class)
    fun list(): MutableList<Message> = messageRepo.findAll()

    @GetMapping("{id}")
    @JsonView(Views.FullMessage::class)
    fun getOne(@PathVariable("id") message: Message): Message = message

    @PostMapping
    fun create(@RequestBody message: Message): Message {
        val savedMessage = messageRepo.save(message)
        wsSender.accept(EventType.CREATE, savedMessage)
        return savedMessage
    }

    @PutMapping("{id}")
    fun update(@PathVariable("id") messageFromDb: Message,
               @RequestBody message: Message): Message {
        BeanUtils.copyProperties(message, messageFromDb, "id")
        val updatedMessage = messageRepo.save(messageFromDb)
        wsSender.accept(EventType.UPDATE, updatedMessage)
        return updatedMessage
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") message: Message) {
        messageRepo.delete(message)
        wsSender.accept(EventType.REMOVE, message)
    }

}
