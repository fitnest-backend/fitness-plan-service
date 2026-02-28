package az.fitnest.fitnessplan.plan.workout.adapter.api;

import az.fitnest.fitnessplan.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Controller for managing workout plans.
 * Provides endpoints for browsing and managing workout plans for users.
 * Currently contains stub functionality for future implementation.
 */
@RestController
@RequestMapping("/api/v1/workout-plans")
@RequiredArgsConstructor
@Tag(name = "Workout Plans", description = "Məşq planlarına baxmaq və idarə etmək üçün ucluqlar")
public class WorkoutPlanController {
    
    @Operation(
            summary = "Bütün məşq planlarını əldə edin",
            description = "Autentifikasiya olunmuş istifadəçi üçün bütün mövcud məşq planlarının siyahısını qaytarır. " +
                    "Bu hal-hazırda boş siyahı qaytaran müvəqqəti ucluqdur."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Məşq planları uğurla əldə edildi (hal-hazırda boşdur)",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "İcazə verilmədi",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Object>>> getWorkoutPlans() {
        return ResponseEntity.ok(ApiResponse.success(Collections.emptyList()));
    }
}
