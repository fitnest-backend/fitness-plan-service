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
@Tag(name = "Nutrition Plans", description = "Qidalanma planlarına baxmaq və idarə etmək üçün ucluqlar")
public class NutritionPlanController {

    private final NutritionPlansService nutritionPlansService;

    @Operation(
            summary = "Bütün qidalanma planlarını əldə edin",
            description = "Autentifikasiya olunmuş istifadəçi üçün bütün mövcud qidalanma planlarının siyahısını qaytarır."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Qidalanma planları uğurla əldə edildi",
                    content = @Content(schema = @Schema(implementation = NutritionPlanResponses.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "İcazə verilmədi",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<NutritionPlanResponses>> getNutritionPlans() {
        return ResponseEntity.ok(ApiResponse.success(nutritionPlansService.getNutritionPlans()));
    }

    @Operation(
            summary = "Qidalanma planını ID vasitəsilə əldə edin",
            description = "Yeməklər və qida dəyərləri daxil olmaqla xüsusi qidalanma planı haqqında ətraflı məlumat qaytarır."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Qidalanma planı təfərrüatları uğurla əldə edildi",
                    content = @Content(schema = @Schema(implementation = NutritionPlanResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "İcazə verilmədi",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Qidalanma planı tapılmadı",
                    content = @Content
            )
    })
    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<NutritionPlanResponse>> getNutritionPlan(
            @Parameter(description = "Qidalanma planının ID-si") @PathVariable Long planId) {
        return ResponseEntity.ok(ApiResponse.success(nutritionPlansService.getNutritionPlan(planId)));
    }

    @Operation(
            summary = "Qidalanma planını aktivləşdirin",
            description = "Autentifikasiya olunmuş istifadəçi üçün xüsusi qidalanma planını aktivləşdirir. " +
                    "Eyni anda yalnız bir qidalanma planı aktiv ola bilər."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Qidalanma planı uğurla aktivləşdirildi",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "İcazə verilmədi",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Qidalanma planı tapılmadı",
                    content = @Content
            )
    })
    @PostMapping("/{planId}/active")
    public ResponseEntity<ApiResponse<Void>> activateNutritionPlan(
            @Parameter(description = "Aktivləşdiriləcək qidalanma planının ID-si") @PathVariable Long planId) {
        nutritionPlansService.activateNutritionPlan(planId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
