package com.spring.data.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public PhotoProduct save(PhotoProduct photoProduct) {
        return entityManager.merge(photoProduct);
    }

    @Transactional
    @Override
    public void delete(PhotoProduct photoProduct) {
        entityManager.remove(photoProduct);
    }

}
