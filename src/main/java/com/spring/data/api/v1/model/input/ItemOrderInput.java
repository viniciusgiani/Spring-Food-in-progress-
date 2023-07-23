package com.spring.data.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemOrderInput {

    @Schema(example = "1")
    @NotNull
    private Long productId;

    @Schema(example = "2")
    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @Schema(example = "No pickles")
    private String observations;

}
