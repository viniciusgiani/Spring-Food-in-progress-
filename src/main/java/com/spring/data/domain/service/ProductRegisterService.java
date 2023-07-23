package com.spring.data.domain.service;

import com.spring.data.domain.exception.ProductNotFoundException;
import com.spring.data.domain.model.Product;
import com.spring.data.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductRegisterService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product searchOrFail(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
    }
}
