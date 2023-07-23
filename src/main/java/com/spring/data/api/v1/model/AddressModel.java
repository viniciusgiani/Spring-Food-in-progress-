package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModel {

    @Schema(example = "00000-000")
    private String zip;

    @Schema(example = "Marshall street")
    private String street;

    @Schema(example = "\"0000\"")
    private String number;

    @Schema(example = "Apt.000")
    private String complement;

    @Schema(example = "Brooklyn")
    private String neighborhood;

    private CityResumeModel city;

}
