package com.spring.data.api.v1.openapi.controller;
import com.spring.data.api.v1.model.KitchenModel;
import com.spring.data.api.v1.model.input.KitchenInput;
import com.spring.data.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Kitchens")
public interface KitchenControllerOpenApi {

    @PageableParameter
    @Operation(summary = "List kitchens with pagination")
    PagedModel<KitchenModel> list(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Search kitchen by id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "invalid kitchen id",
                    content = @Content(schema = @Schema(ref = "Problem"))),
            @ApiResponse(responseCode = "404", description = "Kitchen not found",
                    content = @Content(schema = @Schema(ref = "Problem")))
    })
    KitchenModel search(@Parameter(description = "Kitchen id", example = "1", required = true) Long kitchenId);

    @Operation(summary = "Register kitchen", responses = {
            @ApiResponse(responseCode = "201", description = "Kitchen registered"),
    })
    KitchenModel add(
            @RequestBody(description = "Representation of a new kitchen", required = true) KitchenInput kitchenInput);

    @Operation(summary = "Updates kitchen by id", responses = {
            @ApiResponse(responseCode = "200", description = "Kitchen updated"),
            @ApiResponse(responseCode = "404", description = "Kitchen not found",
                    content = @Content(schema = @Schema(ref = "Problem"))),
    })
    KitchenModel update(
            @Parameter(description = "Kitchen id", example = "1", required = true) Long kitchenId,
            @RequestBody(description = "representation of kitchen with updated data", required = true) KitchenInput kitchenInput);

    @Operation(summary = "Deletes a kitchen by id", responses = {
            @ApiResponse(responseCode = "204", description = "Kitchen deleted"),
            @ApiResponse(responseCode = "404", description = "Kitchen not found",
                    content = @Content(schema = @Schema(ref = "Problem")))
    })
    ResponseEntity<Void> delete(@Parameter(description = "Kitchen id", example = "1", required = true) Long kitchenId);

}
