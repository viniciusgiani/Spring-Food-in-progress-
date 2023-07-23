package com.spring.data.api.v1.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderInput {

    @Valid
    @NotNull
    private RestaurantIdInput restaurant;

    @Valid
    @NotNull
    private AddressInput addressDelivery;

    @Valid
    @NotNull
    private PaymentIdInput payment;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemOrderInput> items;

}
