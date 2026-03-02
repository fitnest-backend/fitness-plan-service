package az.fitnest.fitnessplan.plan.nutrition.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NutritionPlanResponses(
    List<NutritionPlanResponse> nutritionPlans
) {}
