package tars.toy.fleamarket.users.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import tars.toy.fleamarket.users.entities.UserJpaRepository

@Service
class AuthUserService(
    private val userJpaRepository: UserJpaRepository
) {

    suspend fun signIn(email: String, password: String) {
        val user = withContext(Dispatchers.IO) {
            userJpaRepository.findByAuthInfoEmail(email)!!.block()
        }
        if (user!!.authInfo.password != password)
            throw IllegalStateException("패스워드가 일치하지 않습니다.")
    }
}