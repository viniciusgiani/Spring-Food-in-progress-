package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurants")
public interface RestaurantUserResponsibleControllerOpenApi {

    @Operation(summary = "List users responsible associated to restaurant", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = {@Content(schema = @Schema(ref = "Problem")) }),
    })
    CollectionModel<UserModel> list(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Association of restaurant with responsible user", responses = {
            @ApiResponse(responseCode = "204", description = "Associated"),
            @ApiResponse(responseCode = "404", description = "Restaurant ou user not found",
                    content = {@Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> associate(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @Parameter(description = "User id", example = "1", required = true) Long userId);

    @Operation(summary = "Dissociate restaurant with responsible user", responses = {
            @ApiResponse(responseCode = "204", description = "Dissociated"),
            @ApiResponse(responseCode = "404", description = "Restaurant ou user not found",
                    content = {@Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> dissociate(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @Parameter(description = "User id", example = "1", required = true) Long userId);

}