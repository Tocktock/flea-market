package tars.toy.fleamarket.users.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tars.toy.fleamarket.users.entities.*

@Service
class CreateUserService(
    private val userJpaRepository: UserJpaRepository,
    private val tokenJpaRepository: TokenJpaRepository
) {
    @Transactional
    suspend fun createUser(dto: CreateUserDTO): CreateUserResultDTO {
        val (name, phoneNumber, email, password) = dto
        return withContext(Dispatchers.IO) {
            val newUser = userJpaRepository.save(
                User.create(name, phoneNumber, password, email)
            ).block()
            val token = tokenJpaRepository.save(Token(userId = newUser!!.id)).block()
            CreateUserResultDTO(
                token = token!!.id,
                userId = newUser.id
            )
        }
    }

    @Transactional
    suspend fun confirm(token: String) {
        withContext(Dispatchers.IO) {
            val confirmResult =
                tokenJpaRepository.findById(token).block() ?: throw IllegalStateException("토큰 정보가 정확하지 않습니다.")
            val newUser = userJpaRepository.findById(confirmResult.userId).block()
            newUser!!.authInfo.state = State.ACTIVATED
            userJpaRepository.save(newUser).block()
            tokenJpaRepository.delete(confirmResult)
        }
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