package az.fitnest.fitnessplan.shared.exception;

import az.fitnest.fitnessplan.shared.dto.ApiError;
import az.fitnest.fitnessplan.shared.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponse.error(ApiError.builder()
                        .code(ex.getErrorCode())
                        .message(ex.getMessage())
                        .status(ex.getHttpStatus().value())
                        .path(request.getRequestURI())
                        .timestamp(OffsetDateTime.now())
                        .build()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ApiError.builder()
                        .code("VALIDATION_ERROR")
                        .message("Validation failed")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .path(request.getRequestURI())
                        .timestamp(OffsetDateTime.now())
                        .details(details)
                        .build()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ApiError.builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .message("An unexpected error occurred.")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .path(request.getRequestURI())
                        .timestamp(OffsetDateTime.now())
                        .build()));
    }
}
