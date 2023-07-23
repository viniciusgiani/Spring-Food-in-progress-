package com.spring.data.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductInput {

    @Schema(example = "Meat")
    @NotBlank
    private String name;

    @Schema(example = "Raw")
    @NotBlank
    private String description;

    @Schema(example = "0.00")
    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @Schema(example = "true")
    @NotNull
    private Boolean active;

}
