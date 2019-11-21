package kerporation.sarafan.controller

import kerporation.sarafan.exceptions.NotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("message")
class MessageController {

    private var counter: Int = 4

    private var messages: MutableList<Map<String, String>> = mutableListOf(
            mapOf("id" to "1", "text" to "First message"),
            mapOf("id" to "2", "text" to "Second message"),
            mapOf("id" to "3", "text" to "Third message"))

    @GetMapping
    fun list(): List<Map<String, String>> = messages

    @GetMapping("{id}")
    fun findOne(@PathVariable id: String): Map<String, String> = findMessage(id)

    @PostMapping
    fun create(@RequestBody message: MutableMap<String, String>): Map<String, String> {
        message["id"] = counter++.toString()
        messages.add(message)
        return message
    }

    @PutMapping
    fun update(@PathVariable id: String, @RequestBody message: MutableMap<String, String>): Map<String, String> {
        val messageFromDb: MutableMap<String, String> = message["id"]?.let { findMessage(it) } as MutableMap<String, String>?
                ?: throw NotFoundException()
        messageFromDb.putAll(message)
        messageFromDb["id"] = id
        return messageFromDb
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String) {
        val message: Map<String, String> = findMessage(id)
        messages.remove(message)
    }

    private fun findMessage(id: String): Map<String, String> {
        return messages.firstOrNull { it["id"] == id } ?: throw NotFoundException()
    }

}
