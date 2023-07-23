package com.spring.data.domain.event;

import com.spring.data.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCanceledEvent {

    private Order order;

}
