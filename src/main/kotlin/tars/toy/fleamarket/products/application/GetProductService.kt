package tars.toy.fleamarket.products.application

import org.springframework.stereotype.Service
import tars.toy.fleamarket.products.entities.Product
import tars.toy.fleamarket.products.entities.ProductRepository

@Service
class GetProductService(
    private val productRepository: ProductRepository
) {
    fun getById(id: String): Product {
        return productRepository.findById(id).block() ?: throw IllegalStateException("상품 정보가 존재하지 않습니다")
    }
}