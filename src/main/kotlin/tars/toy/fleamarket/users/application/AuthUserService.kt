package tars.toy.fleamarket.users.application

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tars.toy.fleamarket.config.security.JwtFactory
import tars.toy.fleamarket.users.entities.UserJpaRepository

@Service
class AuthUserService(
    private val userJpaRepository: UserJpaRepository,
    private val jwtFactory: JwtFactory,
    private val passwordEncoder: PasswordEncoder
) {

    suspend fun signIn(email: String, password: String): String {
        val user = userJpaRepository.findByAuthInfoEmail(email).awaitSingleOrNull()
            ?: throw IllegalStateException("아이디 또는 패스워드가 일치하지 않습니다.")
        if (user.authInfo.password != passwordEncoder.encode(password))
            throw IllegalStateException("아이디 또는 패스워드가 일치하지 않습니다.")
        return jwtFactory.generate(mapOf("name" to user.name, "email" to user.authInfo.email))
    }

    suspend fun verify(jwt: String) =
        jwtFactory.verifyBearerToken(jwt)

    suspend fun generateJwt(): String {
        return jwtFactory.generate(mapOf("name" to "hehe"))
    }

}