package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.UserModel;
import com.spring.data.api.v1.model.input.PasswordInput;
import com.spring.data.api.v1.model.input.UserInput;
import com.spring.data.api.v1.model.input.UserWithPasswordInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Users")
public interface UserControllerOpenApi {

    @Operation(summary = "List users")
    CollectionModel<UserModel> list();

    @Operation(summary = "Search user by id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid user id", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) }),
    })
    UserModel search(@Parameter(description = "User id", example = "1", required = true) Long userId);

    @Operation(summary = "Register user", responses = {
            @ApiResponse(responseCode = "201", description = "User registered"),
    })
    UserModel add(
            @RequestBody(description = "Representation of new user", required = true) UserWithPasswordInput userInput);

    @Operation(summary = "Updates user by id", responses = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    UserModel update(
            @Parameter(description = "User id", example = "1", required = true) Long userId,
            @RequestBody(description = "Representation of user with new data", required = true) UserInput userInput);

    @Operation(summary = "Updates user password", responses = {
            @ApiResponse(responseCode = "204", description = "Password altered"),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(schema = @Schema(ref = "Problem")) })
    })
    ResponseEntity<Void> alterPassword(
            @Parameter(description = "User id", example = "1", required = true) Long userId,
            @RequestBody(description = "Representation of new password", required = true) PasswordInput newPasswordInput);

}
