package com.spring.data.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Orders")
public interface FlowOrderControllerOpenApi {

    @Operation(summary = "Order confirmation", responses = {
            @ApiResponse(responseCode = "204", description = "Order confirmed"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> confirm(
            @Parameter(description = "Order code", example = "0000-00000-00", required = true) String orderCode);

    @Operation(summary = "Register order delivery", responses = {
            @ApiResponse(responseCode = "204", description = "Deliver registered"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> deliver(
            @Parameter(description = "Order code", example = "00000-00000-00000", required = true) String orderCode);

    @Operation(summary = "Order cancellation", responses = {
            @ApiResponse(responseCode = "204", description = "Order cancelled"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> cancel(
            @Parameter(description = "Order code", example = "00000-00000-00000", required = true) String orderCode);

}
