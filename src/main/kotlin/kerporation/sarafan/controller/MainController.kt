package kerporation.sarafan.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import kerporation.sarafan.domain.User
import kerporation.sarafan.domain.Views
import kerporation.sarafan.repo.MessageRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MainController(@Autowired private val messageRepo: MessageRepo,
                     @Value("\${spring.profiles.active}") private val profile: String,
                     private val mapper: ObjectMapper) {

    private val writer: ObjectWriter = mapper.setConfig(mapper.serializationConfig).writerWithView(Views.FullMessage::class.java)

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User?): String {
        val data: HashMap<Any, Any?> = hashMapOf()
        if (user != null) {
            data["profile"] = user
            val messages = writer.writeValueAsString(messageRepo.findAll())
            model.addAttribute("messages", messages)
        }
        model.addAttribute("frontendData", data)
        model.addAttribute("isDevMode", "dev" == profile)
        return "index"
    }
}
