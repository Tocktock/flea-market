package tars.toy.fleamarket.products.application

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import tars.toy.fleamarket.products.entities.Product
import tars.toy.fleamarket.products.entities.ProductJpaRepository

@Service
class GetProductService(
    private val productRepository: ProductJpaRepository
) {
    suspend fun getById(id: String): Product {
        return productRepository.findById(id).awaitSingle() ?: throw IllegalStateException("상품 정보가 존재하지 않습니다")
    }
}