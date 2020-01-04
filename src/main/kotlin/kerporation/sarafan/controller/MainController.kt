package kerporation.sarafan.controller

import kerporation.sarafan.domain.User
import kerporation.sarafan.repo.MessageRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MainController(@Autowired private val messageRepo: MessageRepo) {

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User?): String {
        val data: HashMap<Any, Any?> = hashMapOf()
        data["profile"] = user
        data["messages"] = messageRepo.findAll()
        model.addAttribute("frontendData", data)
        return "index"
    }
}
