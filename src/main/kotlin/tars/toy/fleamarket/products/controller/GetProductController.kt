package tars.toy.fleamarket.products.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import tars.toy.fleamarket.common.ResponseDTO
import tars.toy.fleamarket.products.application.GetProductService

@RestController
class GetProductController(
    private val getProductService: GetProductService,
) {
    @GetMapping("/products/{id}")
    suspend fun getProduct(
        @PathVariable id: String
    ): ResponseDTO {
        return ResponseDTO(ok = true, data = getProductService.getById(id))
    }
}