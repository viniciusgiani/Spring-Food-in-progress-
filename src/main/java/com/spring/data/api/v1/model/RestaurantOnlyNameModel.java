package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantOnlyNameModel extends RepresentationModel<RestaurantOnlyNameModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Japanese food")
    private String name;

}