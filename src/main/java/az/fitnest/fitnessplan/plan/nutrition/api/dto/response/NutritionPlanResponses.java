package az.fitnest.fitnessplan.plan.nutrition.api.dto.response;

import java.util.List;

public record NutritionPlanResponses(
    List<NutritionPlanResponse> nutritionPlans
) {
    public static NutritionPlanResponsesBuilder builder() {
        return new NutritionPlanResponsesBuilder();
    }

    public static class NutritionPlanResponsesBuilder {
        private List<NutritionPlanResponse> nutritionPlans;

        public NutritionPlanResponsesBuilder() {}

        public NutritionPlanResponsesBuilder nutritionPlans(List<NutritionPlanResponse> nutritionPlans) {
            this.nutritionPlans = nutritionPlans;
            return this;
        }

        public NutritionPlanResponses build() {
            return new NutritionPlanResponses(nutritionPlans);
        }
    }
}
