package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.PartyModel;
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
@Tag(name = "Users")
public interface UserPartyControllerOpenApi {
    @Operation(summary = "List parties associated to a user", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    CollectionModel<PartyModel> list(
            @Parameter(description = "User id", example = "1", required = true) Long userId);

    @Operation(summary = "Associated of party with user", responses = {
            @ApiResponse(responseCode = "204", description = "Associated"),
            @ApiResponse(responseCode = "404", description = "User or party not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> associate(
            @Parameter(description = "User id", example = "1", required = true) Long userId,
            @Parameter(description = "Party id", example = "1", required = true) Long partyId);

    @Operation(summary = "Dissociation of party with user", responses = {
            @ApiResponse(responseCode = "204", description = "Dissociated"),
            @ApiResponse(responseCode = "404", description = "User or party not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> dissociate(
            @Parameter(description = "User id", example = "1", required = true) Long userId,
            @Parameter(description = "Party id", example = "1", required = true) Long partyId);

}
