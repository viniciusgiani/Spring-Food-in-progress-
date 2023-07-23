package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.StateModel;
import com.spring.data.api.v1.model.input.StateInput;
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
@Tag(name = "State")
public interface StateControllerOpenApi {

    @Operation(summary = "List states")
    CollectionModel<StateModel> list();

    @Operation(summary = "Search state by id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid state id", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "State not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    StateModel search(@Parameter(description = "State id", example = "1", required = true) Long stateId);

    @Operation(summary = "Register state", responses = {
            @ApiResponse(responseCode = "201", description = "State registered")
    })
    StateModel add(
            @RequestBody(description = "Representation of new state", required = true) StateInput stateInput);

    @Operation(summary = "Updates state by id", responses = {
            @ApiResponse(responseCode = "200", description = "State updated"),
            @ApiResponse(responseCode = "404", description = "State not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    StateModel update(
            @Parameter(description = "State id", example = "1", required = true) Long stateId,
            @RequestBody(description = "Representation of updated state", required = true) StateInput stateInput);

    @Operation(summary = "Deletes state by id", responses = {
            @ApiResponse(responseCode = "204", description = "State deleted"),
            @ApiResponse(responseCode = "404", description = "State not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    ResponseEntity<Void> delete(@Parameter(description = "State id", example = "1", required = true) Long stateId);

}
