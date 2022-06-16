package tars.toy.fleamarket.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtFactory {
    @Value("\${keys.jwt.secret}")
    private val secret: String? = null

    @Value("\${keys.jwt.expiration-time}")
    private val expirationTime: Long? = null

    private var key: SecretKey? = null

    fun generate(claims: Map<String, Any>): String {
        val createdDate = Date()
        val expirationDate = Date(createdDate.time + expirationTime!! * 1000)
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
            .setIssuer("fleaMarket") // (2)
            .setIssuedAt(createdDate)
            .addClaims(claims)
            .setExpiration(expirationDate)
            .signWith(getJwtKey())
            .compact()
    }

    fun verify(jwt: String): Map<String, Any> {
        val result = Jwts.parserBuilder().setSigningKey(getJwtKey()).build().parseClaimsJws(jwt)
        result.body.forEach {
            println(it.value)
        }
        return result.body.toMap()
    }

    fun getJwtKey(): SecretKey? {
//        Keys.secretKeyFor(SignatureAlgorithm.HS512)
        return this.key ?: Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret!!)).also { this.key = it }
    }

    fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getAllClaimsFromToken(token).expiration
    }
}