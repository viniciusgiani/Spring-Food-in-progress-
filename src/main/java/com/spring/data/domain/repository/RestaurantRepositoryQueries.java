package com.spring.data.domain.repository;

import com.spring.data.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
    List<Restaurant> find(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee);

    List<Restaurant> findWithFreeDelivery(String name);
}
