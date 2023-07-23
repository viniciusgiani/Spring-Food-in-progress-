package com.spring.data.infrastructure.repository;

import com.spring.data.domain.repository.CustomJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> searchFirst() {
        var jpql = "from " + getDomainClass().getName();

        T entity = entityManager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();
        return Optional.ofNullable(entity);
    }

    @Override
    public void detach(T entity) {
        entityManager.detach(entity);
    }
}
