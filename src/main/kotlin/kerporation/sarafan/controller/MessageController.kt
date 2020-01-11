package kerporation.sarafan.controller

import com.fasterxml.jackson.annotation.JsonView
import kerporation.sarafan.WsSender
import kerporation.sarafan.domain.Message
import kerporation.sarafan.domain.Views
import kerporation.sarafan.dto.EventType
import kerporation.sarafan.dto.MetaDto
import kerporation.sarafan.dto.ObjectType
import kerporation.sarafan.repo.MessageRepo
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.util.function.BiConsumer
import java.util.regex.Pattern

@RestController
@RequestMapping("message")
class MessageController @Autowired constructor(
        private val messageRepo: MessageRepo,
        wsSenderInstance: WsSender
        ) {

    private val wsSender: BiConsumer<EventType, Message> = wsSenderInstance.getSender(ObjectType.MESSAGE, Views.Id::class.java)

    companion object {
        private const val URL_PATTERN: String = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+"
        private const val IMAGE_PATTERN: String = "\\.(jpeg|jpg|gif|png)\$"
        private val URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE)
        private val IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE)

    }

    @GetMapping
    @JsonView(Views.IdName::class)
    fun list(): MutableList<Message> = messageRepo.findAll()

    @GetMapping("{id}")
    @JsonView(Views.FullMessage::class)
    fun getOne(@PathVariable("id") message: Message): Message = message

    @PostMapping
    fun create(@RequestBody message: Message): Message {
        fillMeta(message)
        val savedMessage = messageRepo.save(message)
        wsSender.accept(EventType.CREATE, savedMessage)
        return savedMessage
    }

    @PutMapping("{id}")
    fun update(@PathVariable("id") messageFromDb: Message,
               @RequestBody message: Message): Message {
        BeanUtils.copyProperties(message, messageFromDb, "id")
        fillMeta(messageFromDb)
        val updatedMessage = messageRepo.save(messageFromDb)
        wsSender.accept(EventType.UPDATE, updatedMessage)
        return updatedMessage
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") message: Message) {
        messageRepo.delete(message)
        wsSender.accept(EventType.REMOVE, message)
    }

    private fun fillMeta(message: Message) {
        val text: String = message.text
        var matcher = URL_REGEX.matcher(text)
        if (matcher.find()) {
            val url = text.substring(matcher.start(), matcher.end())
            matcher = IMG_REGEX.matcher(url)
            message.link = url
            if (matcher.find()) {
                message.link = url
            } else if (!url.contains("youtu")) {
                val meta: MetaDto = getMeta(url)
                message.linkCover = meta.cover
                message.linkTitle = meta.title
                message.linkDescription = meta.description
            }
        }
    }

    private fun getMeta(url: String): MetaDto {
        val doc = Jsoup.connect(url).get()
        val title = doc.select("meta[name$=title],meta[property$=title]")
        val description = doc.select("meta[name$=description],meta[property$=description]")
        val cover = doc.select("meta[name$=image],meta[property$=image]")
        return MetaDto(
                title.first()?.attr("content") ?: "",
                description.first()?.attr("content") ?: "",
                cover.first()?.attr("content") ?: ""
        )
    }
}
