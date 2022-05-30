package tars.toy.fleamarket.common

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtFactory {
    private val key =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode("mypasswordmypasswordmypasswordmypasswordmypasswordmypassword"))

    fun generate(claims: Map<String, Any>): String =
        Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
            .setIssuer("fleaMarket") // (2)
            .setIssuedAt(Date())
            .addClaims(claims)
            .signWith(key)
            .compact()

    fun verify(jwt: String): Map<String, Any> {
        val result = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt)
        result.body.forEach {
            println(it.value)
        }
        return result.body.toMap()
    }
}