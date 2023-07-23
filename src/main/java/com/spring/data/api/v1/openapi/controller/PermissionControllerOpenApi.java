package com.spring.data.api.v1.openapi.controller;

import com.spring.data.api.v1.model.PermissionModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Permissions")
public interface PermissionControllerOpenApi {

    @Operation(summary = "List permissions")
    CollectionModel<PermissionModel> list();

}
