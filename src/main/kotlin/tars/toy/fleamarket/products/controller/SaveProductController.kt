package tars.toy.fleamarket.products.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.config.security.JwtFactory
import tars.toy.fleamarket.products.application.SaveProductService
import tars.toy.fleamarket.products.application.SaveProductServiceDTO
import tars.toy.fleamarket.products.entities.UserInfo

@RestController
class SaveProductController(
    private val saveProductService: SaveProductService,
    private val jwtFactory: JwtFactory
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/products")
    suspend fun savePost(
        @RequestBody body: SaveProductReqDTO,
        @RequestHeader(name = "Authorization") authHeader: String,
    ): ResponseDTO {
        val name = jwtFactory.verify(authHeader.split(" ")[1])["name"]
        if (name != body.name) throw RuntimeException("넌 못지나간다")
        val result = saveProductService.save(
            SaveProductServiceDTO(
                name = body.name,
                price = body.price,
                seller = body.seller,
            )
        )
        return ResponseDTO(ok = true, data = SaveProductResDTO(result.id))
    }
}

data class SaveProductReqDTO(
    val name: String,
    val price: Int,
    val seller: UserInfo
)

data class SaveProductResDTO(
    val productId: String,
)