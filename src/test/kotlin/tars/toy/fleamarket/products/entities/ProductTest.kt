package tars.toy.fleamarket.products.entities

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProductTest {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun save() {
        productRepository.save(Product(id = "abcdefg", price = 20000, name = "맥북")).block()
        val entity = productRepository.findById("abcdefg").block()
        println(entity?.id)
    }
}