package tars.toy.fleamarket.users.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.users.application.AuthUserService

@RestController
class AuthUserController(
    private val authUserService: AuthUserService
) {
    @PostMapping("/users/sign-in")
    suspend fun signIn(@RequestBody body: SignInReqDTO): ResponseDTO {
        authUserService.signIn(body.id, body.password)
        return ResponseDTO(ok = true, data = null)
    }

    @PostMapping("/jwt/verify")
    suspend fun verify(@RequestBody body: VerifyTokenReqDTO): ResponseDTO {
        val result = authUserService.verify(body.jwt)
        return ResponseDTO(ok = true, data = result)
    }
}

data class SignInReqDTO(
    val id: String,
    val password: String,
)

data class VerifyTokenReqDTO(
    val jwt: String,
)