package az.fitnest.fitnessplan.plan.nutrition.api.dto.response;

import lombok.Builder;

@Builder
public record NutritionPlanResponse(
    Long planId,
    String title,
    String description,
    Boolean isActive
) {}
