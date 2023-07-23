package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.PaymentModel;
import com.spring.data.api.v1.model.input.PaymentInput;
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
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Payment methods")
public interface PaymentControllerOpenApi {

    @Operation(summary = "List payment methods")
    ResponseEntity<CollectionModel<PaymentModel>> list(@Parameter(hidden = true) ServletWebRequest request);

    @Operation(summary = "Search payment method by id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid payment id", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "Payment method not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    ResponseEntity<PaymentModel> search(
            @Parameter(description = "Payment method id", example = "1", required = true) Long paymentId,
            @Parameter(hidden = true) ServletWebRequest request);

    @Operation(summary = "Register payment method", responses = {
            @ApiResponse(responseCode = "201", description = "Payment method registered")})
    PaymentModel add(
            @RequestBody(description = "Representation of a new payment method", required = true) PaymentInput paymentInput);

    @Operation(summary = "Update payment method by id", responses = {
            @ApiResponse(responseCode = "200", description = "Payment method updated"),
            @ApiResponse(responseCode = "404", description = "Payment method not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    PaymentModel update(
            @Parameter(description = "Payment method id", example = "1", required = true) Long paymentId,
            @RequestBody(description = "Representation of a payment method with updated data", required = true) PaymentInput paymentInput);

    @Operation(summary = "Deletes payment method by id", responses = {
            @ApiResponse(responseCode = "204", description = "Payment method deleted"),
            @ApiResponse(responseCode = "404", description = "Payment method not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Payment method id", example = "1", required = true) Long paymentId);

}
