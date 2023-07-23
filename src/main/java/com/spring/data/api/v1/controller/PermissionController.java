package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.PermissionModelAssembler;
import com.spring.data.api.v1.model.PermissionModel;
import com.spring.data.api.v1.openapi.controller.PermissionControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.Permission;
import com.spring.data.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Override
    @CheckSecurity.UsersPartyPermissions.CanQuery
    @GetMapping
    public CollectionModel<PermissionModel> list() {
        List<Permission> allPermissions = permissionRepository.findAll();

        return permissionModelAssembler.toCollectionModel(allPermissions);
    }
}
