package com.spring.data.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.data.core.validation.Groups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "delivery_fee",nullable = false)
    private BigDecimal deliveryFee;

    @ManyToOne
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @Embedded
    private Address address;

    private Boolean active = Boolean.TRUE;

    private Boolean open = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime registerData;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime updateDate;

    @ManyToMany
    @JoinTable(name = "restaurant_payment",
    joinColumns = @JoinColumn(name = "restaurant_id"),
    inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private Set<Payment> payments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurant_user_responsible",
    joinColumns = @JoinColumn(name = "restaurant_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> responsible = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public void activate() {
        setActive(true);
    }

    public void inactive() {
        setActive(false);
    }

    public void open() {
        setOpen(true);
    }

    public void close() {
        setOpen(false);
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public boolean isInactive() {
        return !isOpen();
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean openPermitted() {
        return isActive() && isClosed();
    }

    public boolean activatePermitted() {
        return isActive() && isClosed();
    }

    public boolean inactivePermitted() {
        return isActive();
    }

    public boolean closePermitted() {
        return isOpen();
    }

    public boolean removePaymentMethod(Payment payment) {
        return getPayments().remove(payment);
    }

    public boolean addPaymentMethod(Payment payment) {
        return getPayments().add(payment);
    }

    public boolean acceptPaymentMethod(Payment payment) {
        return getPayments().contains(payment);
    }

    public boolean notAcceptPaymentMethod(Payment payment) {
        return !acceptPaymentMethod(payment);
    }

    public boolean removeResponsible(User user) {
        return getResponsible().remove(user);
    }

    public boolean addResponsible(User user) {
        return getResponsible().add(user);
    }
}
