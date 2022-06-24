package tars.toy.fleamarket.common

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.WebExchangeBindException
import java.time.LocalDateTime

@RestController
class SomeController {

    @PostMapping("/some-api")
    fun sdf(@RequestBody @Valid someBody: SomeBody): String {
        return "your request is successfully processed"
    }

    @GetMapping("/some-get-api")
    fun someGet(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) someField: LocalDateTime): String {
        return "your request is successfully processed"
    }
}

data class SomeBody(
    @field:NotNull val name: String?,
    @field:NotNull val notNullField: String?,
    @field:NotNull val notNullInt: Int?,
    @field:Positive val age: Int,
)

//data class SomeBody(
//    @field:NotEmpty val someList: List<String>,
//    val name: String,
//    val someEnum: SomeEnum,
//    val myTime: LocalDateTime,
//    @JsonFormat(
//        shape = JsonFormat.Shape.STRING,
//        pattern = "yyyy-MM-dd HH:mm:ss",
//        timezone = "Asia/Seoul"
//    ) val someTime: LocalDateTime,
//)
//
// enum class SomeEnum {
//    FIRST,
//    SECOND,
//    THIRD
// }
//
// data class TestBody(
//    @field:Email val email: String,
//    @field:Min(15) @Max(30) val age: Int?,
//    @field:NotNull val name: String,
//    @field:NotNull val isHuman: Boolean?,
// )
//
// data class TestInsideClass(
//    @field:Email val insideEmail: String
// )

@ControllerAdvice
class ErrorHandling {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(value = [WebExchangeBindException::class])
    suspend fun webExchangeBindExceptionHandler(e: WebExchangeBindException): ResponseEntity<Any> {
        val errors = ErrorFrame(
            errors = e.fieldErrors.map {
                ErrorContent(
                    type = "INVALID_PARAMETER",
                    message = it.defaultMessage ?: e.localizedMessage,
                    fieldName = it.field,
                    rejectedValue = it.rejectedValue.toString()
                )
            })
        logger.error(e.message, e)
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [FleaMarketException::class])
    suspend fun fleaMarketExceptionHandler(e: FleaMarketException): ResponseEntity<Any> {
        val error = ErrorFrame(
            errors = listOf(
                ErrorContent(
                    type = "FLEA_MARKET_EXCEPTION",
                    message = e.message ?: e.localizedMessage,
                    fieldName = null,
                    rejectedValue = null
                )
            )
        )
        logger.error(e.message, e)
        return ResponseEntity(error, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(value = [Exception::class])
    suspend fun notCatchedException(e: Exception): ResponseEntity<Any> {
        val error = ErrorFrame(
            errors = listOf(
                ErrorContent(
                    type = "UNKNOWN_EXCEPTION",
                    message = e.message ?: e.localizedMessage,
                    fieldName = null,
                    rejectedValue = null
                )
            )
        )
        logger.error(e.message, e)
        return ResponseEntity(error, HttpStatus.FORBIDDEN)
    }
}

class FleaMarketException(message: String) : RuntimeException(message)

data class ErrorFrame(
    val errors: List<ErrorContent>
)

data class ErrorContent(
    val type: String,
    val message: String,
    val fieldName: String?,
    val rejectedValue: String?,
)
