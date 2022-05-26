package tars.toy.fleamarket.products.entities

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProductTest {

    @Autowired
    lateinit var productRepository: ProductJpaRepository

    @Test
    fun contextLoads() {
        productRepository.findById("hehe").block()
        val a = productRepository.save(
            Product(
                id = "someUUID",
                price = 5000,
                name = "마이가전",
                buyer = null,
                seller = UserInfo(
                    id = "someUserId",
                    name = "someUserName",
                    phoneNumber = "01012341234"
                )
            )
        ).block()
        val saved = productRepository.findById("someUUID").block()
        println(saved!!.name)
    }
//
//    @Test
//    suspend fun subscribeTest() {
//        val a = productRepository.save(
//            Product(
//                id = "someUUID2",
//                price = 5000,
//                name = "마이가전",
//                buyer = null,
//                seller = UserInfo(
//                    id = "someUserId",
//                    name = "someUserName",
//                    phoneNumber = "01012341234"
//                )
//            )
//        ).subscribe() {
//            println(it.name)
//        }
//        delay(1000)
//    }
}