package ea.home.gcp.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<Map<String, String>> {
        val body = mapOf(
            "error" to ex.message.orEmpty(),
            "type" to ex::class.simpleName.orEmpty()
        )
        return ResponseEntity(body, HttpStatus.SERVICE_UNAVAILABLE)
    }
}
