package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.PermissionModel;
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
@Tag(name = "Parties")
public interface PartyPermissionControllerOpenApi {
    @Operation(summary = "List of permissions associated to party", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid party id", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "Party not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    CollectionModel<PermissionModel> list(
            @Parameter(description = "Party id", example = "1", required = true) Long partyId);

    @Operation(summary = "Associate permission with party", responses = {
            @ApiResponse(responseCode = "204", description = "Associated"),
            @ApiResponse(responseCode = "404", description = "Party or permission not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> associate(
            @Parameter(description = "Party id", example = "1", required = true) Long partyId,
            @Parameter(description = "Permission id", example = "1", required = true) Long permissionId);

    @Operation(summary = "Dissociate party with permission", responses = {
            @ApiResponse(responseCode = "204", description = "Dissociated"),
            @ApiResponse(responseCode = "404", description = "Party or permission not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> dissociate(
            @Parameter(description = "Party id", example = "1", required = true) Long partyId,
            @Parameter(description = "Permission id", example = "1", required = true) Long permissionId);

}
