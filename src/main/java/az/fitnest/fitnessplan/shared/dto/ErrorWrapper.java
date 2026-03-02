package az.fitnest.fitnessplan.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorWrapper {
    private @JsonProperty("error") ErrorDetail error;
    private String code;
    private String message;
    private List<FieldIssue> details;
    private String field;
    private String issue;



    public static ErrorWrapper fromErrorResponse(ErrorResponse errorResponse) {
        List<FieldIssue> details = null;
        if (errorResponse.getDetails() != null && errorResponse.getDetails().containsKey("validationErrors")) {
            @SuppressWarnings("unchecked")
            Map<String, String> validationErrors = (Map<String, String>) errorResponse.getDetails().get("validationErrors");
            if (validationErrors != null) {
                details = validationErrors.entrySet().stream()
                        .map(entry -> FieldIssue.builder()
                                .field(entry.getKey())
                                .issue(entry.getValue())
                                .build())
                        .toList();
            }
        }

        return ErrorWrapper.builder()
                .error(ErrorDetail.builder()
                        .code(errorResponse.getCode())
                        .message(errorResponse.getMessage())
                        .details(details)
                        .build())
                .build();
    }

    public static class ErrorDetail {
    }

    public static class FieldIssue {
    }
}
