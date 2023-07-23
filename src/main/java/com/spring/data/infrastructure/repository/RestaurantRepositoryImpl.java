package com.spring.data.infrastructure.repository;

import com.spring.data.domain.model.Restaurant;
import com.spring.data.domain.repository.RestaurantRepository;
import com.spring.data.domain.repository.RestaurantRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.spring.data.infrastructure.repository.specifications.RestaurantSpecs.withFreeDelivery;
import static com.spring.data.infrastructure.repository.specifications.RestaurantSpecs.withSimilarName;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Lazy
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> find(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        var root = criteriaQuery.from(Restaurant.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(name)) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }

        if (initialDeliveryFee != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deliveryFee"), initialDeliveryFee));
        }

        if (initialDeliveryFee != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deliveryFee"), finalDeliveryFee));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeDelivery(String name) {
        return restaurantRepository.findAll(withFreeDelivery().and(withSimilarName(name)));
    }
}
