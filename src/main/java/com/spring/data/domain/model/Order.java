package com.spring.data.domain.model;

import com.spring.data.domain.event.OrderCanceledEvent;
import com.spring.data.domain.event.OrderConfirmedEvent;
import com.spring.data.domain.exception.BusinessException;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Order extends AbstractAggregateRoot<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalValue;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancellationDate;
    private OffsetDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ItemOrder> items = new ArrayList<>();

    public void calculateTotalValue() {
        getItems().forEach(ItemOrder::calculateTotalPrice);

        this.subtotal = getItems().stream()
                .map(itemOrder -> itemOrder.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalValue = this.subtotal.add(this.deliveryFee);
    }

    public void confirm() {
        setStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now());

        registerEvent(new OrderConfirmedEvent(this));
    }

    public void deliver() {
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now());
    }

    public void cancel() {
        setStatus(OrderStatus.CANCELED);
        setDeliveryDate(OffsetDateTime.now());

        registerEvent(new OrderCanceledEvent(this));
    }

    public boolean canBeDelivered() {
        return getStatus().canNotAlterTo(OrderStatus.CONFIRMED);
    }

    public boolean canBeCanceled() {
        return getStatus().canNotAlterTo(OrderStatus.DELIVERED);
    }

    public boolean canBeConfirmed() {
        return getStatus().canNotAlterTo(OrderStatus.CANCELED);
    }

    public void setStatus(OrderStatus newStatus) {
        if(getStatus().canNotAlterTo(newStatus)) {
            throw new BusinessException(
                    String.format("Status %s can not be changed from %s to %s",
                            getCode(), getStatus().getDescription(), newStatus.getDescription())
            );
        }
        this.status = newStatus;
    }

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
