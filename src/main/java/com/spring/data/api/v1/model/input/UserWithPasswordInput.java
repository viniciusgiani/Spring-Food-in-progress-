package com.spring.data.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithPasswordInput extends UserInput{

    @Schema(example = "root", type = "string")
    @NotBlank
    private String password;

}
