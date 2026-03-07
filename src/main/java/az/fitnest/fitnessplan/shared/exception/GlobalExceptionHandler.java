package az.fitnest.fitnessplan.shared.exception;

import az.fitnest.fitnessplan.shared.dto.ApiError;
import az.fitnest.fitnessplan.shared.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponse.error(ApiError.builder()
                        .code(ex.getErrorCode())
                        .message(getLocalizedMessage(ex.getErrorCode(), ex.getMessage()))
                        .status(ex.getHttpStatus().value())
                        .path(request.getRequestURI())
                        .timestamp(OffsetDateTime.now())
                        .build()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        java.util.List<java.util.Map<String, String>> fieldIssues = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> java.util.Map.of(
                        "field", error.getField(),
                        "issue", safeMessage(error.getDefaultMessage())
                ))
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ApiError.builder()
                        .code("VALIDATION_ERROR")
                        .message(getMessage("error.validation"))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .path(request.getRequestURI())
                        .timestamp(OffsetDateTime.now())
                        .details(java.util.Map.of("fieldIssues", fieldIssues))
                        .build()));
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ApiError.builder()
                        .code("BAD_REQUEST")
                        .message(getMessage("error.invalid_json_format"))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .path(request.getRequestURI())
                        .timestamp(OffsetDateTime.now())
                        .build()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ApiError.builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .message(getMessage("error.internal_server_error"))
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .path(request.getRequestURI())
                        .timestamp(OffsetDateTime.now())
                        .build()));
    }

    private String getLocalizedMessage(String errorCode, String defaultMessage) {
        String key = "error." + errorCode.toLowerCase();
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (org.springframework.context.NoSuchMessageException e1) {
            try {
                return messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale());
            } catch (org.springframework.context.NoSuchMessageException e2) {
                return safeMessage(defaultMessage);
            }
        }
    }

    private String safeMessage(String msg) {
        if (msg == null || msg.isBlank()) {
            return getMessage("error.unexpected");
        }
        if (msg.startsWith("error.")) {
            String resolved = getMessage(msg);
            if (!resolved.equals(msg)) {
                return resolved;
            }
        }
        return msg;
    }

    private String getMessage(String code) {
        try {
            return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (org.springframework.context.NoSuchMessageException e) {
            return code;
        }
    }
}
