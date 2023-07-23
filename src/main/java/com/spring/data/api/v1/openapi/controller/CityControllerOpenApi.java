package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.CityModel;
import com.spring.data.api.v1.model.input.CityInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cities")
public interface CityControllerOpenApi {

    @Operation(summary = "List cities")
    CollectionModel<CityModel> list();

    @Operation(summary = "Search for id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "Invalid city id",
                            content = @Content(schema = @Schema(ref = "Problem"))
                    ),
                    @ApiResponse(responseCode = "404", description = "City not found",
                            content = @Content(schema = @Schema(ref = "Problem"))
                    )
            })
    CityModel search(@Parameter(description = "City id", example = "1", required = true) Long cityId);

    @Operation(summary = "Register city", description = "Register city, " +
            "needs valid state and city")
    CityModel add(@RequestBody(description = "Representation of a new city", required = true) CityInput cityInput);

    @Operation(summary = "Update city by id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "Invalid city id",
                            content = @Content(schema = @Schema(ref = "Problem"))
                    ),
                    @ApiResponse(responseCode = "404", description = "City not found",
                            content = @Content(schema = @Schema(ref = "Problem"))
                    )
            })
    CityModel update(@Parameter(description = "City id", example = "1", required = true) Long cityId,
                          @RequestBody(description = "Representation of an updated city", required = true) CityInput cityInput);

    @Operation(summary = "Delete city by id",responses = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "Invalid city id",
                    content = @Content(schema = @Schema(ref = "Problem"))
            ),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(ref = "Problem"))
            )
    })
    ResponseEntity<Void> delete(@Parameter(description = "City id", example = "1", required = true)Long cityId);

}
