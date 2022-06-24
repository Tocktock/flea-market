package tars.toy.fleamarket.users.application

import io.kotest.core.spec.style.StringSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class AuthUserServiceTest(
    private val createUserService: CreateUserService
) : StringSpec({

    "signUp"() {
        val user = createUserService.createUser(
            CreateUserDTO(
                name = "테스트 유저",
                phoneNumber = "01012341234",
                email = "qnfmtm666@naver.com",
                password = "mypassw0rd1234",
            )
        )
    }
})