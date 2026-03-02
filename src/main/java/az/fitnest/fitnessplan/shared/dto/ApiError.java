package az.fitnest.fitnessplan.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        String code,
        String message,
        Integer status,
        String path,
        OffsetDateTime timestamp,
        Object details
) {
    public ApiError {
        if (timestamp == null) {
            timestamp = OffsetDateTime.now();
        }
    }
}
