package com.spring.data.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class ItemOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer quantity;
    private String observation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    public void calculateTotalPrice() {
        BigDecimal unitPrice = this.getUnitPrice();
        Integer quantity = this.getQuantity();

        if(unitPrice == null) {
            unitPrice = BigDecimal.ZERO;
        }

        if (quantity == null) {
            quantity = 0;
        }

        this.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
    }
}
