package tars.toy.fleamarket.products.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository


@Document(collection = "products")
class Product(
    @Id val id: String,
    val price: Int,
    val name: String,
)

@Repository
interface ProductRepository : ReactiveMongoRepository<Product, String>