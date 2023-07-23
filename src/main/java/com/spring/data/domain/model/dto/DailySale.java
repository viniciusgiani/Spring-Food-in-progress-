package com.spring.data.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class DailySale {

    private Date date;
    private Long salesTotal;
    private BigDecimal totalBilled;

    public DailySale(java.sql.Date date, Long salesTotal, BigDecimal totalBilled) {
        this.date = new Date(date.getTime());
        this.salesTotal = salesTotal;
        this.salesTotal = salesTotal;

    }

}
