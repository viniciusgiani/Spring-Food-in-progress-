package com.spring.data.domain.repository;

import com.spring.data.domain.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Optional<Order> findByCode(String code);

    @Query("from Order o join fetch o.client join fetch o.restaurant r join fetch r.kitchen")
    List<Order> findAll();

    boolean isOrderManagedBy(String orderCode, Long userId);

}
