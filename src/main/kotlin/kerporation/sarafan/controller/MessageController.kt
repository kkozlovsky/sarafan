package kerporation.sarafan.controller

import kerporation.sarafan.exceptions.NotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("message")
class MessageController {

    private var messages: List<Map<String, String>> = mutableListOf(
            mapOf("id" to "1", "text" to "First message"),
            mapOf("id" to "2", "text" to "Second message"),
            mapOf("id" to "3", "text" to "Third message"))

    @GetMapping
    fun list(): List<Map<String, String>> = messages

    @GetMapping("{id}")
    fun findOne(@PathVariable id: String): Map<String, String> {
        return messages
                .filter { it.get("id") == id }
                .firstOrNull() ?: throw NotFoundException()
    }


}
