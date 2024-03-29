package tars.toy.fleamarket.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Component
class PBKDF2Encoder : PasswordEncoder {
    @Value("\${keys.password.secret}")
    private val secret: String? = null

    @Value("\${keys.password.iteration}")
    private val iteration: Int? = null

    @Value("\${keys.password.key-length}")
    private val keyLength: Int? = null

    override fun encode(cs: CharSequence): String {
        return try {
            val result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                .generateSecret(
                    PBEKeySpec(
                        cs.toString().toCharArray(), secret!!.toByteArray(), iteration!!,
                        keyLength!!
                    )
                )
                .encoded
            Base64.getEncoder().encodeToString(result)
        } catch (ex: NoSuchAlgorithmException) {
            throw RuntimeException(ex)
        } catch (ex: InvalidKeySpecException) {
            throw RuntimeException(ex)
        }
    }

    override fun matches(cs: CharSequence, string: String): Boolean {
        return encode(cs) == string
    }
}