package com.spring.data.domain.repository;

import com.spring.data.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.kitchen")
    List<Restaurant> findAll();

    List<Restaurant> queryByDeliveryFeeBetween(BigDecimal initialFee, BigDecimal finalFee);

    List<Restaurant> queryByName(String name, @Param("id") Long kitchen);

    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countById(Long kitchen);

    boolean existsResponsible(Long restaurantId, Long userId);

}
