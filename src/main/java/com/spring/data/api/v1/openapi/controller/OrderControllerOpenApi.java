package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.OrderModel;
import com.spring.data.api.v1.model.OrderResumeModel;
import com.spring.data.api.v1.model.input.OrderInput;
import com.spring.data.core.springdoc.PageableParameter;
import com.spring.data.domain.filter.OrderFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Orders")
public interface OrderControllerOpenApi {

    @Operation(
            summary = "Orders search",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "clientId",
                            description = "Client id for search filter",
                            example = "1", schema = @Schema(type = "integer")),
                    @Parameter(in = ParameterIn.QUERY, name = "restaurantId",
                            description = "Restaurant id for search filter",
                            example = "1", schema = @Schema(type = "integer")),
                    @Parameter(in = ParameterIn.QUERY, name = "creationDateStart",
                            description = "Date/time creation start for search filter",
                            example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
                    @Parameter(in = ParameterIn.QUERY, name = "creationDateEnd",
                            description = "Date/time creation final for search filter",
                            example = "2019-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
            }
    )
    @PageableParameter
    PagedModel<OrderResumeModel> search(
            @Parameter(hidden = true) OrderFilter filter,
            @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Search for order code", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {
                    @Content(schema = @Schema(ref = "Problem"))}),
    })
    OrderModel search(@Parameter(description = "Order code", example = "0000-00000",
            required = true) String codeOrder);

    @Operation(summary = "Register an order", responses = {
            @ApiResponse(responseCode = "201", description = "Order registered"),
    })
    OrderModel add(
            @RequestBody(description = "Representation of a new order", required = true) OrderInput orderInput);
}
