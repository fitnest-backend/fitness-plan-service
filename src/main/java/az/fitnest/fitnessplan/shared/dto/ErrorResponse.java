package az.fitnest.fitnessplan.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String message,
    String code,
    LocalDateTime timestamp,
    String path,
    Map<String, Object> details
) {
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
