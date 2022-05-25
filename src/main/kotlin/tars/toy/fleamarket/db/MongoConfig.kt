//package tars.toy.fleamarket.db
//
//import com.mongodb.MongoClientSettings
//import com.mongodb.MongoCredential
//import com.mongodb.reactivestreams.client.MongoClient
//import com.mongodb.reactivestreams.client.MongoClients
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
//
//
//@Configuration
//class MongoConfig : AbstractReactiveMongoConfiguration() {
//    @Bean
//    fun mongoClient(): MongoClient {
//        return MongoClients.create(
//            MongoClientSettings.builder()
//                .credential(
//                    MongoCredential.createCredential(
//                        "mongoadmin",
//                        "fleaMarket",
//                        "mypassw0rd".toCharArray()
//                    )
//                ).build()
//        )
//    }
//
//    override fun getDatabaseName(): String {
//        return "fleaMarket"
//    }
//}