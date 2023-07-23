package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.PaymentModel;
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
public interface RestaurantPaymentControllerOpenApi {

    @Operation(summary = "List payment methods associated with restaurants", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    CollectionModel<PaymentModel> list(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Associate restaurant with payment method", responses = {
            @ApiResponse(responseCode = "204", description = "Associated"),
            @ApiResponse(responseCode = "404", description = "Restaurant or payment method not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> associate(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Payment id", example = "1", required = true) Long paymentId);

    @Operation(summary = "Dissociate restaurant with payment method", responses = {
            @ApiResponse(responseCode = "204", description = "Dissociated"),
            @ApiResponse(responseCode = "404", description = "Restaurant or payment method not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ResponseEntity<Void> dissociate(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Payment id", example = "1", required = true) Long paymentId);


}
