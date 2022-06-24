package tars.toy.fleamarket.chat.application

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import tars.toy.fleamarket.chat.entities.ChatJpaRepository
import tars.toy.fleamarket.chat.entities.Chats
import java.time.LocalDateTime
import java.util.*

@Service
class ChatService(
    private val chatJpaRepository: ChatJpaRepository
) {

    suspend fun saying(command: SayingCommand) {
        chatJpaRepository.save(
            Chats(
                id = UUID.randomUUID().toString(),
                speaker = command.speakerEmail,
                message = command.message,
                topic = command.topic,
            )
        ).awaitSingle()
    }

    suspend fun getChat(topic: String, lastTime: LocalDateTime): List<Chats> {
        return chatJpaRepository.findByTopic(topic).awaitSingle()
    }
}

data class SayingCommand(
    val topic: String,
    val speakerEmail: String,
    val message: String,
)