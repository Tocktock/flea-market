package tars.toy.fleamarket.users.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.users.application.AuthUserService

@RestController
class AuthUserController(
    private val authUserService: AuthUserService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @PostMapping("/users/sign-in")
    suspend fun signIn(@RequestBody body: SignInReqDTO): ResponseDTO {
       return try {
            ResponseDTO(ok = true, data = authUserService.signIn(body.id, body.password))
        }catch (e : RuntimeException){
           logger.error(e.message, e)
            ResponseDTO(ok = false, data = null)
        }
    }

    @GetMapping("/jwt/generate")
    suspend fun createJwt(): ResponseDTO {
        return ResponseDTO(ok = true, data = authUserService.generateJwt())
    }

    @GetMapping("/jwt/confirm/{jwt}")
    suspend fun confirm(@PathVariable jwt: String): ResponseDTO {
        return ResponseDTO(ok = true, data = authUserService.verify(jwt))
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