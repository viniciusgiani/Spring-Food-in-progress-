package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissions")
@Getter
@Setter
public class PermissionModel extends RepresentationModel<PermissionModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "QUERY_KITCHENS")
    private String name;

    @Schema(example = "Allow query kitchens")
    private String description;

}
