package kerporation.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import kerporation.sarafan.domain.Message
import kerporation.sarafan.domain.Views
import kerporation.sarafan.repo.MessageRepo
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("message")
class MessageController @Autowired constructor(
        private val messageRepo: MessageRepo
) {

    @GetMapping
    @JsonView(Views.IdName::class)
    fun list(): MutableList<Message> = messageRepo.findAll()

    @GetMapping("{id}")
    @JsonView(Views.FullMessage::class)
    fun getOne(@PathVariable("id") message: Message): Message = message

    @PostMapping
    fun create(@RequestBody message: Message): Message {
        return messageRepo.save(message)
    }

    @PutMapping("{id}")
    fun update(@PathVariable("id") messageFromDb: Message,
               @RequestBody message: Message): Message {
        BeanUtils.copyProperties(message, messageFromDb, "id")
        return messageRepo.save(messageFromDb)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") message: Message) {
        messageRepo.delete(message)
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    fun change(message: Message) : Message {
        return messageRepo.save(message)
    }

}
