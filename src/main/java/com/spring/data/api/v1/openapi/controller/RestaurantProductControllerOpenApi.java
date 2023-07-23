package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.ProductModel;
import com.spring.data.api.v1.model.input.ProductInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Products")
public interface RestaurantProductControllerOpenApi {

    @Operation(summary = "List restaurant products", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid restaurant id", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    CollectionModel<ProductModel> list(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Include nonactives", example = "false", required = false) Boolean includeNonactives);

    @Operation(summary = "Search product restaurant", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid product or restaurant id", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "Restaurant product not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ProductModel search(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Product id", example = "1", required = true) Long productId);

    @Operation(summary = "Register restaurant product", responses = {
            @ApiResponse(responseCode = "201", description = "Product registered"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ProductModel add(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @RequestBody(description = "Representation of new product", required = true) ProductInput productInput);

    @Operation(summary = "Update restaurant product", responses = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Restaurant product not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    ProductModel update(
            @Parameter(description = "Restaurant id", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Product id", example = "1", required = true) Long productId,
            @RequestBody(description = "Representation of updated product", required = true) ProductInput productInput);


}
