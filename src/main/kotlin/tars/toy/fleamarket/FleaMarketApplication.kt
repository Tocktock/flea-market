package tars.toy.fleamarket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class FleaMarketApplication

fun main(args: Array<String>) {
    runApplication<FleaMarketApplication>(*args)
}
