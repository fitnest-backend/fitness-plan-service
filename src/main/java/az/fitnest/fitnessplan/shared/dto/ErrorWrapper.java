package az.fitnest.fitnessplan.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public record ErrorWrapper(
        @JsonProperty("error") ErrorDetail error,
        String code,
        String message,
        List<FieldIssue> details,
        String field,
        String issue
) {
    public static ErrorWrapperBuilder builder() {
        return new ErrorWrapperBuilder();
    }

    public static class ErrorWrapperBuilder {
        private ErrorDetail error;
        private String code;
        private String message;
        private List<FieldIssue> details;
        private String field;
        private String issue;

        ErrorWrapperBuilder() {}

        public ErrorWrapperBuilder error(ErrorDetail error) {
            this.error = error;
            return this;
        }

        public ErrorWrapperBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorWrapperBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorWrapperBuilder details(List<FieldIssue> details) {
            this.details = details;
            return this;
        }

        public ErrorWrapperBuilder field(String field) {
            this.field = field;
            return this;
        }

        public ErrorWrapperBuilder issue(String issue) {
            this.issue = issue;
            return this;
        }

        public ErrorWrapper build() {
            return new ErrorWrapper(error, code, message, details, field, issue);
        }
    }
    public static ErrorWrapper fromErrorResponse(ErrorResponse errorResponse) {
        List<FieldIssue> details = null;
        if (errorResponse.details() != null && errorResponse.details().containsKey("validationErrors")) {
            @SuppressWarnings("unchecked")
            Map<String, String> validationErrors = (Map<String, String>) errorResponse.details().get("validationErrors");
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
                        .code(errorResponse.code())
                        .message(errorResponse.message())
                        .details(details)
                        .build())
                .build();
    }

    public record ErrorDetail(
            String code,
            String message,
            List<FieldIssue> details
    ) {
        public static ErrorDetailBuilder builder() {
            return new ErrorDetailBuilder();
        }

        public static class ErrorDetailBuilder {
            private String code;
            private String message;
            private List<FieldIssue> details;

            ErrorDetailBuilder() {}

            public ErrorDetailBuilder code(String code) {
                this.code = code;
                return this;
            }

            public ErrorDetailBuilder message(String message) {
                this.message = message;
                return this;
            }

            public ErrorDetailBuilder details(List<FieldIssue> details) {
                this.details = details;
                return this;
            }

            public ErrorDetail build() {
                return new ErrorDetail(code, message, details);
            }
        }
    }

    public record FieldIssue(
            String field,
            String issue
    ) {
        public static FieldIssueBuilder builder() {
            return new FieldIssueBuilder();
        }

        public static class FieldIssueBuilder {
            private String field;
            private String issue;

            FieldIssueBuilder() {}

            public FieldIssueBuilder field(String field) {
                this.field = field;
                return this;
            }

            public FieldIssueBuilder issue(String issue) {
                this.issue = issue;
                return this;
            }

            public FieldIssue build() {
                return new FieldIssue(field, issue);
            }
        }
    }
}
