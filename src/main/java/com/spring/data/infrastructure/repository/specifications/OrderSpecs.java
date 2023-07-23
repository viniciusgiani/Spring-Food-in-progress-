package com.spring.data.infrastructure.repository.specifications;

import com.spring.data.domain.filter.OrderFilter;
import com.spring.data.domain.model.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, query, builder) -> {
            if (Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("kitchen");
                root.fetch("client");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreationDateStart() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"),
                        filter.getCreationDateStart()));
            }

            if (filter.getCreationDateEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"),
                        filter.getCreationDateEnd()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
