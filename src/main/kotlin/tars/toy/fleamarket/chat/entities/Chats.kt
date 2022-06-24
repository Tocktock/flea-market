package tars.toy.fleamarket.chat.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Document(collection = "chats")
class Chats(
    @Id val id: String, // 얘는 필요 없을지도.
    @Indexed val speaker: String, // email 로 하것소.
    val message: String,
    @Indexed val topic: String,
    @Indexed(direction = IndexDirection.DESCENDING) @CreatedDate val createAt: LocalDateTime = LocalDateTime.now(),
)


@Repository
interface ChatJpaRepository : ReactiveMongoRepository<Chats, String> {
    fun findByTopic(topic: String): Mono<List<Chats>>
}