package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.controller.StatisticsController;
import com.spring.data.domain.filter.DailySaleFilter;
import com.spring.data.domain.model.dto.DailySale;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SecurityRequirement(name = "security_auth")
@Tag(name = "Statistics")
public interface StatisticsControllerOpenApi {

    @Operation(hidden = true)
    StatisticsController.StatisticsModel statistics();

    @Operation(
            summary = "Query daily sales statistics",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "restaurantId", description = "Restaurant id", example = "1", schema = @Schema(type = "integer")),
                    @Parameter(in = ParameterIn.QUERY, name = "CreationDateStart", description = "Date/time order creation start", example = "2023-07-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
                    @Parameter(in = ParameterIn.QUERY, name = "CreationDateEnd", description = "Date/time order creation end", example = "2023-07-02T00:00:01Z", schema = @Schema(type = "string", format = "date-time"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DailySale.class))),
                            @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary")),
                    }),
            }
    )
    List<DailySale> queryDailySales(
            @Parameter(hidden = true) DailySaleFilter filter,
            @Parameter(description = "Time delay compared to UTC", schema = @Schema(type = "string", defaultValue = "+00:00")) String timeOffset
    );

    @Operation(hidden = true)
    ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter filter, String timeOffset);


}
