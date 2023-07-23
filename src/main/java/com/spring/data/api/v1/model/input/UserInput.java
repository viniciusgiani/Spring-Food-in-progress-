package com.spring.data.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

    @Schema(example = "John Doe")
    @NotBlank
    private String name;

    @Schema(example = "john@email.com")
    @NotBlank
    @Email
    private String email;

}
