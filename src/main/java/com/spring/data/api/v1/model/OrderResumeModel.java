package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "orders")
@Setter
@Getter
public class OrderResumeModel extends RepresentationModel<OrderResumeModel> {

    @Schema(example = "000-000-000000000")
    private String code;

    @Schema(example = "0.00")
    private BigDecimal subtotal;

    @Schema(example = "0.00")
    private BigDecimal deliveryFee;

    @Schema(example = "0.00")
    private BigDecimal totalValue;

    @Schema(example = "CREATED")
    private String status;

    @Schema(example = "2023-07-22T08:41:01Z")
    private OffsetDateTime creationDate;

    private RestaurantOnlyNameModel restaurant;
    private UserModel client;

}
