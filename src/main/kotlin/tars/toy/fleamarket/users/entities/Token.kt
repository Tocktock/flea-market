package tars.toy.fleamarket.users.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Document(collection = "tokens")
class Token(
    @Id val id: String = UUID.randomUUID().toString(),
    val userId: String,
)

@Repository
interface TokenJpaRepository : ReactiveMongoRepository<Token, String> {
    fun findByUserId(userId: String): Mono<Token>
}