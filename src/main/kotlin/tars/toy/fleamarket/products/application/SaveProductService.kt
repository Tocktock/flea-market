package tars.toy.fleamarket.products.application

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import tars.toy.fleamarket.products.entities.Product
import tars.toy.fleamarket.products.entities.ProductJpaRepository
import tars.toy.fleamarket.products.entities.UserInfo
import java.util.*

@Service
class SaveProductService(
    private val productRepository: ProductJpaRepository
) {
    @Transactional
    suspend fun save(dto: SaveProductServiceDTO): Product {
        return productRepository.save(
            Product(
                id = UUID.randomUUID().toString(),
                price = dto.price,
                name = dto.name,
                buyer = null,
                seller = UserInfo(
                    id = dto.seller.id,
                    name = dto.seller.name,
                    phoneNumber = dto.seller.phoneNumber,
                )
            )
        ).awaitSingle()
    }
}

data class SaveProductServiceDTO(
    val name: String,
    val price: Int,
    val seller: UserInfo
)