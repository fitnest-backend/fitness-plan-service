package az.fitnest.fitnessplan.plan.nutrition.adapter.api;

import az.fitnest.fitnessplan.plan.nutrition.adapter.service.NutritionPlansService;
import az.fitnest.fitnessplan.plan.nutrition.api.dto.response.NutritionPlanResponse;
import az.fitnest.fitnessplan.plan.nutrition.api.dto.response.NutritionPlanResponses;
import az.fitnest.fitnessplan.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing nutrition plans.
 * Provides endpoints for browsing, viewing, and activating nutrition plans for users.
 */
@RestController
@RequestMapping("/api/v1/nutrition-plans")
@RequiredArgsConstructor
@Tag(name = "Nutrition Plans", description = "Endpoints for browsing and managing nutrition plans")
public class NutritionPlanController {

    private final NutritionPlansService nutritionPlansService;

    @Operation(
            summary = "Get all nutrition plans",
            description = "Returns a list of all available nutrition plans for the authenticated user."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Nutrition plans retrieved successfully",
                    content = @Content(schema = @Schema(implementation = NutritionPlanResponses.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<NutritionPlanResponses>> getNutritionPlans() {
        return ResponseEntity.ok(ApiResponse.success(nutritionPlansService.getNutritionPlans()));
    }

    @Operation(
            summary = "Get nutrition plan by ID",
            description = "Returns detailed information about a specific nutrition plan including meals and nutritional breakdown."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Nutrition plan details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = NutritionPlanResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Nutrition plan not found",
                    content = @Content
            )
    })
    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<NutritionPlanResponse>> getNutritionPlan(
            @Parameter(description = "ID of the nutrition plan") @PathVariable Long planId) {
        return ResponseEntity.ok(ApiResponse.success(nutritionPlansService.getNutritionPlan(planId)));
    }

    @Operation(
            summary = "Activate a nutrition plan",
            description = "Activates a specific nutrition plan for the authenticated user. " +
                    "Only one nutrition plan can be active at a time."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Nutrition plan activated successfully",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Nutrition plan not found",
                    content = @Content
            )
    })
    @PostMapping("/{planId}/active")
    public ResponseEntity<ApiResponse<Void>> activateNutritionPlan(
            @Parameter(description = "ID of the nutrition plan to activate") @PathVariable Long planId) {
        nutritionPlansService.activateNutritionPlan(planId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
