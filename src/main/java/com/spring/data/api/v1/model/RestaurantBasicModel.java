package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantBasicModel extends RepresentationModel<RestaurantBasicModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Japanese kitchen")
    private String name;

    @Schema(example = "0.00")
    private BigDecimal deliveryFee;
    private KitchenModel kitchen;

}
