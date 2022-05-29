package tars.toy.fleamarket.users.application

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tars.toy.fleamarket.users.entities.*

@Service
class CreateUserService(
    private val userJpaRepository: UserJpaRepository,
    private val tokenJpaRepository: TokenJpaRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    suspend fun createUser(dto: CreateUserDTO): CreateUserResultDTO {
        val (name, phoneNumber, email, password) = dto
        val newUser = userJpaRepository.save(
            User.create(name, phoneNumber, passwordEncoder.encode(password), email)
        ).awaitSingle()
        val token = tokenJpaRepository.save(Token(userId = newUser!!.id)).awaitSingle()
        return CreateUserResultDTO(
            token = token.id,
            userId = newUser.id
        )
    }

    @Transactional
    suspend fun confirm(token: String) {
        val confirmResult =
            tokenJpaRepository.findById(token).awaitSingleOrNull() ?: throw IllegalStateException("토큰 정보가 정확하지 않습니다.")
        val newUser = userJpaRepository.findById(confirmResult.userId).awaitSingle()
        newUser!!.authInfo.state = State.ACTIVATED
        userJpaRepository.save(newUser).awaitSingle()
        tokenJpaRepository.delete(confirmResult).awaitSingle()
    }
}

data class CreateUserDTO(
    val name: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
)

data class CreateUserResultDTO(
    val token: String,
    val userId: String,
)