package com.spring.data.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressInput {

    @Schema(example = "00000-000")
    @NotBlank
    private String zip;

    @Schema(example = "Marshall street")
    @NotBlank
    private String street;

    @Schema(example = "apt. 000")
    private String complement;

    @Schema(example = "Brooklyn")
    @NotBlank
    private String neighborhood;

    @Valid
    @NotNull
    private CityInput city;

}
