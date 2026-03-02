package az.fitnest.fitnessplan.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String message,
        String code,
        LocalDateTime timestamp,
        String path,
        Map<String, Object> details
) {
    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    public static class ErrorResponseBuilder {
        private String message;
        private String code;
        private LocalDateTime timestamp;
        private String path;
        private Map<String, Object> details;

        ErrorResponseBuilder() {}

        public ErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponseBuilder details(Map<String, Object> details) {
            this.details = details;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(message, code, timestamp, path, details);
        }
    }
    public static ErrorResponse of(String message, String code) {
        return ErrorResponse.builder()
                .message(message)
                .code(code)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(String message, String code, String path) {
        return ErrorResponse.builder()
                .message(message)
                .code(code)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(String message, String code, String path, Map<String, Object> details) {
        return ErrorResponse.builder()
                .message(message)
                .code(code)
                .path(path)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
