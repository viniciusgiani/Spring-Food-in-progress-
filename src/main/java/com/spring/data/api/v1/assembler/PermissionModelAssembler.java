package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.model.PermissionModel;
import com.spring.data.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissionModelAssembler implements RepresentationModelAssembler<Permission, PermissionModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    public PermissionModel toModel(Permission permission) {
        PermissionModel permissionModel = modelMapper.map(permission, PermissionModel.class);
        return permissionModel;
    }

    @Override
    public CollectionModel<PermissionModel> toCollectionModel(Iterable<? extends Permission> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(foodLinks.linkToPermissions());
    }
}
