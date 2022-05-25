package tars.toy.fleamarket.products.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.products.application.SaveProductService
import tars.toy.fleamarket.products.application.SaveProductServiceDTO
import tars.toy.fleamarket.products.entities.UserInfo

@RestController
class SaveProductController(
    private val saveProductService: SaveProductService
) {
    @PostMapping("/products")
    fun savePost(
        @RequestBody body: SaveProductReqDTO
    ): ResponseDTO {
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