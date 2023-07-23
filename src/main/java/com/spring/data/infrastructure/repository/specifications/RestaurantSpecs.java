package com.spring.data.infrastructure.repository.specifications;

import com.spring.data.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {
    public static Specification<Restaurant> withFreeDelivery() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("deliveryFee"), BigDecimal.ZERO));
    }

    public static Specification<Restaurant> withSimilarName(String name) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }
}
