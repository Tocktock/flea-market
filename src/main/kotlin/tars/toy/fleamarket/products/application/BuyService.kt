package tars.toy.fleamarket.products.application

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tars.toy.fleamarket.common.FleaMarketException
import tars.toy.fleamarket.products.entities.Product
import tars.toy.fleamarket.products.entities.ProductJpaRepository
import tars.toy.fleamarket.products.entities.UserInfo
import tars.toy.fleamarket.users.entities.UserJpaRepository

@Service
class BuyService(
    private val userJpaRepository: UserJpaRepository,
    private val productJpaRepository: ProductJpaRepository
) {
    @Transactional
    suspend fun buy(buyerEmail: String, productId: String): Product {
        val user = userJpaRepository.findByAuthInfoEmail(buyerEmail).awaitSingleOrNull()
            ?: throw FleaMarketException("유저 업지롱")
        val product = productJpaRepository.findById(productId).awaitSingle()
        product.setBuyer(UserInfo(user.id, user.name, user.phoneNumber))
        return productJpaRepository.save(product).awaitSingle()
    }
}