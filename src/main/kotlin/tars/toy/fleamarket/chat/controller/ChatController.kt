package tars.toy.fleamarket.chat.controller

import org.springframework.web.bind.annotation.*
import tars.toy.fleamarket.chat.application.ChatService
import tars.toy.fleamarket.chat.application.SayingCommand
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.config.security.JwtFactory
import java.time.LocalDateTime

@RestController
class ChatController(
    private val jwtFactory: JwtFactory,
    private val chatService: ChatService,
) {
    @PostMapping("/chats/saying")
    suspend fun saying(
        @RequestBody body: SayingDTO,
        @RequestHeader(name = "Authorization") authHeader: String,
    ): ResponseDTO {
        val email = jwtFactory.verifyBearerToken(authHeader)["email"]
        chatService.saying(SayingCommand(body.topic, email.toString(), body.message))
        return ResponseDTO(ok = true, data = null)
    }

    @GetMapping("/chats/{topic}")
    suspend fun getChat(@PathVariable topic: String): ResponseDTO {
        return ResponseDTO(ok = true, data = chatService.getChat(topic, LocalDateTime.now()))
    }
}

data class SayingDTO(
    val message: String,
    val topic: String,
)