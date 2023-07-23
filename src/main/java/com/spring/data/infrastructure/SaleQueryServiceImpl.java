package com.spring.data.infrastructure;

import com.spring.data.domain.filter.DailySaleFilter;
import com.spring.data.domain.model.Order;
import com.spring.data.domain.model.OrderStatus;
import com.spring.data.domain.model.dto.DailySale;
import com.spring.data.domain.service.SaleQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> queryDailySales(DailySaleFilter filter, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySale.class);
        var root = query.from(Order.class);
        var predicates = new ArrayList<Predicate>();

        var functionConvertTzCreationDate = builder.function(
                "convert_tz", Date.class, root.get("creationDate"),
                builder.literal("+00:00"), builder.literal(timeOffset));

        var functionDateCreationDate = builder.function(
                "date", Date.class, functionConvertTzCreationDate);

        var selection = builder.construct(DailySale.class,
                functionDateCreationDate,
                builder.count(root.get("id")),
                builder.sum(root.get("totalValue")));

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

        predicates.add(root.get("status").in(
                OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateCreationDate);

        return manager.createQuery(query).getResultList();
    }
}
