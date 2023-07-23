package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.PartyModel;
import com.spring.data.api.v1.model.input.PartyInput;
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
@Tag(name = "Parties")
public interface PartyControllerOpenApi {

    @Operation(summary = "List parties")
    CollectionModel<PartyModel> list();

    @Operation(summary = "Search party by id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid party id",
                    content = {@Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "Party not found",
                    content = {@Content(schema = @Schema(ref = "Problem")) }),
    })
    PartyModel search(@Parameter(description = "Party id", example = "1", required = true) Long partyId);

    @Operation(summary = "Register party", responses = {
            @ApiResponse(responseCode = "201", description = "Party registered"),
    })
    PartyModel add(
            @RequestBody(description = "Representation of novo party", required = true) PartyInput partyInput);

    @Operation(summary = "Updates party by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Party updated"),
            @ApiResponse(responseCode = "404", description = "Party not found", content = {
                    @Content(schema = @Schema(ref = "Problem"))
            }),
    })
    PartyModel update(
            @Parameter(description = "ID of party", example = "1", required = true) Long partyId,
            @RequestBody(description = "Representation of party with new data", required = true) PartyInput partyInput);

    @Operation(summary = "Deletes party by id", responses = {
            @ApiResponse(responseCode = "204", description = "Party deleted"),
            @ApiResponse(responseCode = "404", description = "Party not found", content = {
                    @Content(schema = @Schema(ref = "Problem"))
            }),
    })
    ResponseEntity<Void> delete(@Parameter(description = "Party id") Long partyId);

}
