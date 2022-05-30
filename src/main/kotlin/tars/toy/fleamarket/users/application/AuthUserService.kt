package tars.toy.fleamarket.users.application

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import tars.toy.fleamarket.common.JwtFactory
import tars.toy.fleamarket.users.entities.UserJpaRepository

@Service
class AuthUserService(
    private val userJpaRepository: UserJpaRepository,
    private val jwtFactory: JwtFactory,
) {

    suspend fun signIn(email: String, password: String) {
        val user = userJpaRepository.findByAuthInfoEmail(email).awaitSingleOrNull()
            ?: throw IllegalStateException("아이디 또는 패스워드가 일치하지 않습니다.")
        if (user.authInfo.password != password)
            throw IllegalStateException("아이디 또는 패스워드가 일치하지 않습니다.")
    }

    suspend fun verify(jwt: String) =
        jwtFactory.verify(jwt)

}