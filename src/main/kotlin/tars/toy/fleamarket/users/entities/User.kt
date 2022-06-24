package tars.toy.fleamarket.users.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Document(collection = "users")
class User(
    @Id val id: String = UUID.randomUUID().toString(),
    val name: String,
    val phoneNumber: String,
    val address: String?,
    val addressDetail: String?,
    val authInfo: AuthInfo,
    val roles: List<Role>,
    @CreatedDate val createAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun create(name: String, phoneNumber: String, password: String, email: String) = User(
            name = name,
            phoneNumber = phoneNumber,
            authInfo = AuthInfo(
                password = password,
                email = email,
                state = State.DEACTIVATED,
            ),
            address = null,
            addressDetail = null,
            roles = listOf(Role.ROLE_USER)
        )
    }
}

data class AuthInfo(
    val password: String,
    val email: String,
    var state: State,
)

enum class State {
    DEACTIVATED,
    ACTIVATED,
    DROPOUT
}

enum class Role {
    ROLE_USER, ROLE_ADMIN
}

@Repository
interface UserJpaRepository : ReactiveMongoRepository<User, String> {
    fun findByAuthInfoEmail(email: String): Mono<User?>
}