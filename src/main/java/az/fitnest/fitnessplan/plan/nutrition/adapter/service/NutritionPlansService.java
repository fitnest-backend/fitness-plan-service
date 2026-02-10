package az.fitnest.fitnessplan.plan.nutrition.adapter.service;

import az.fitnest.fitnessplan.plan.nutrition.api.dto.response.NutritionPlanResponse;
import az.fitnest.fitnessplan.plan.nutrition.api.dto.response.NutritionPlanResponses;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service for managing nutrition plans.
 * Provides functionality to browse, view details, and activate nutrition plans.
 *
 * <p>Note: This is currently a stub implementation with pending business logic.
 * Future implementation will include:
 * <ul>
 *   <li>Meal planning and nutritional breakdown</li>
 *   <li>Personalized nutrition recommendations</li>
 *   <li>Calorie and macro tracking</li>
 *   <li>Plan activation and scheduling</li>
 * </ul>
 */
@Service
public class NutritionPlansService {

    /**
     * Retrieves all available nutrition plans for the user.
     *
     * @return response containing list of nutrition plans (currently empty)
     */
    public NutritionPlanResponses getNutritionPlans() {
        return NutritionPlanResponses.builder()
                .nutritionPlans(Collections.emptyList())
                .build();
    }

    /**
     * Retrieves detailed information about a specific nutrition plan.
     *
     * @param planId the ID of the nutrition plan
     * @return detailed nutrition plan information (currently returns null)
     */
    public NutritionPlanResponse getNutritionPlan(Long planId) {
        return null;
    }

    /**
     * Activates a nutrition plan for the current user.
     * Only one plan can be active at a time.
     *
     * @param planId the ID of the plan to activate
     */
    public void activateNutritionPlan(Long planId) {
        // Implementation pending
    }
}
