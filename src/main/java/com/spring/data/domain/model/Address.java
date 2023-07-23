package com.spring.data.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Address {

    @Column(name = "address_zip")
    private String zip;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    private City city;
}
