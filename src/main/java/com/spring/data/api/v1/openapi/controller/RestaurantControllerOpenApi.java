package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.RestaurantBasicModel;
import com.spring.data.api.v1.model.RestaurantModel;
import com.spring.data.api.v1.model.RestaurantOnlyNameModel;
import com.spring.data.api.v1.model.input.RestaurantInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurants")
public interface RestaurantControllerOpenApi {

    @Operation(summary = "List restaurants", parameters = {
            @Parameter(name = "projection",
                    description = "Projection Name",
                    example = "name-only",
                    in = ParameterIn.QUERY,
                    required = false
            )
    })
    CollectionModel<RestaurantBasicModel> list();

    @Operation(hidden = true)
    CollectionModel<RestaurantOnlyNameModel> listOnlyName();

    @Operation(summary = "Search restaurant by id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid restaurant id", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    RestaurantModel search(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Register a restaurant", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurant registered"),
    })
    RestaurantModel add(
            @RequestBody(description = "Representation of new restaurant", required = true) RestaurantInput restaurantInput);

    @Operation(summary = "Updates restaurant by id", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    RestaurantModel update(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @RequestBody(description = "Representation of updated restaurant", required = true) RestaurantInput restaurantInput);

    @Operation(summary = "Activates restaurant by id", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant activated"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> activate(@Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Deactivate restaurant by id", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant deactivated"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> inactivate(@Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Activate multiple restaurants", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurants activated"),
    })
    ResponseEntity<Void> activateMultiples(
            @RequestBody(description = "Restaurants ids", required = true) List<Long> restaurantIds);

    @Operation(summary = "Deactivate multiple restaurants", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurants activated"),
    })
    ResponseEntity<Void> inactivateMultiples(
            @RequestBody(description = "Restaurants ids", required = true) List<Long> restaurantIds);

    @Operation(summary = "Open restaurant by id", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant opened"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> open(@Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Close restaurant by id", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant closed"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> close(@Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId);

}
