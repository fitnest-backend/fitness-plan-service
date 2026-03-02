package az.fitnest.fitnessplan.shared.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageCriteria {

    @Builder

    @Builder

    private String sortBy;

    @Builder

    public Integer getPage() {
        return page != null && page > 0 ? page - 1 : 0;
    }

    public enum SortDirection {
        ASC, DESC
    }
}
