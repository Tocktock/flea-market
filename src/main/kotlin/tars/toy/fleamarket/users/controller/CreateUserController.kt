package tars.toy.fleamarket.users.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.users.application.CreateUserDTO
import tars.toy.fleamarket.users.application.CreateUserService

@RestController
class CreateUserController(
    private val createUserService: CreateUserService
) {

    @PostMapping("/users")
    suspend fun createUser(
        @RequestBody @Valid body: CreateUserReqDTO
    ): ResponseDTO {
        // validation neede
        val result = createUserService.createUser(
            CreateUserDTO(body.name, body.phoneNumber, body.email, body.password)
        )
        return ResponseDTO(ok = true, data = result)
    }

    @PostMapping("/users/confirm")
    suspend fun confirmUser(
        @RequestBody @Valid body: ConfirmUserReqDTO
    ): ResponseDTO {
        createUserService.confirm(body.jwt)
        return ResponseDTO(ok = true, data = null)
    }
}

data class CreateUserReqDTO(
    @field:NotBlank
    @field:NotNull(message = "이름이 없습니다") val name: String,
    @field:NotNull(message = "연락처가 없습니다") val phoneNumber: String,
    @field:NotNull(message = "비밀번호가 없습니다") val password: String,
    @field:Email(message = "이메일이 없습니다.") val email: String,
)

data class ConfirmUserReqDTO(
    @field:NotBlank val jwt: String,
)