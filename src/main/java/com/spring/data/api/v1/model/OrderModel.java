package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "orders")
@Setter
@Getter
public class OrderModel extends RepresentationModel<OrderModel> {

    @Schema(example = "0000000-0000-000-00")
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

    @Schema(example = "2023-07-22T08:41:01Z")
    private OffsetDateTime confirmationDate;

    @Schema(example = "2023-07-22T08:41:01Z")
    private OffsetDateTime deliveryDate;

    @Schema(example = "2023-07-22T08:41:01Z")
    private OffsetDateTime cancellationDate;

    private RestaurantOnlyNameModel restaurant;
    private UserModel client;
    private PaymentModel paymentModel;
    private AddressModel addressDelivery;
    private List<ItemOrderModel> items;

}
