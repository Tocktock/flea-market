package tars.toy.fleamarket.products.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Document(collection = "products")
class Product(
    @Id val id: String,
    val price: Int,
    val name: String,
    val buyer: UserInfo?,
    val seller: UserInfo,
    @CreatedDate val createAt: LocalDateTime = LocalDateTime.now(),
)

data class UserInfo(
    val id: String,
    val name: String,
    val phoneNumber: String,
)

@Repository
interface ProductJpaRepository : ReactiveMongoRepository<Product, String>