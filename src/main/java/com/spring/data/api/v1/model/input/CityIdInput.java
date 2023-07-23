package com.spring.data.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityIdInput {

    @Schema(example = "1")
    @NotNull
    private StateIdInput state;

}
