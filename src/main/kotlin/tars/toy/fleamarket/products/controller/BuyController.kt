package tars.toy.fleamarket.products.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import tars.toy.fleamarket.common.FleaMarketException
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.config.security.JwtFactory
import tars.toy.fleamarket.products.application.BuyService

@RestController
class BuyController(
    private val jwtFactory: JwtFactory,
    private val buyService: BuyService,
) {

    @PostMapping("/products/buy")
    suspend fun buy(
        @RequestBody body: BuyReqDTO,
        @RequestHeader(name = "Authorization") authHeader: String,
    ): ResponseDTO {
        val email = jwtFactory.verifyBearerToken(authHeader)["email"]
            ?: throw FleaMarketException("이메일 없다~")
        val product = buyService.buy(email.toString(), body.productId)
        return ResponseDTO(ok = true, data = product)
    }
}

data class BuyReqDTO(
    val productId: String,
)