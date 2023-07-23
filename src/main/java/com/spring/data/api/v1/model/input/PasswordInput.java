package com.spring.data.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordInput {

    @Schema(example = "root", type = "string")
    @NotBlank
    private String currentPassword;

    @Schema(example = "root", type = "string")
    @NotBlank
    private String newPassword;

}
