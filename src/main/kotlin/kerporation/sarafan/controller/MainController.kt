package kerporation.sarafan.controller

import kerporation.sarafan.domain.User
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
                     @Value("\${spring.profiles.active}") private val profile: String) {

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User?): String {
        val data: HashMap<Any, Any?> = hashMapOf()
        if (user != null) {
            data["profile"] = user
            data["messages"] = messageRepo.findAll()
        }
        model.addAttribute("frontendData", data)
        model.addAttribute("isDevMode", "dev" == profile)
        return "index"
    }
}
