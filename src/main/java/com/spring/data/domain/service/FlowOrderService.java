package com.spring.data.domain.service;

import com.spring.data.domain.model.Order;
import com.spring.data.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowOrderService {

    @Autowired
    private EmissionOrderService emissionOrderService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void confirm(String orderCode) {
        Order order = emissionOrderService.searchOrFail(orderCode);
        order.confirm();

        orderRepository.save(order);
    }

    @Transactional
    public void cancel(String orderCode) {
        Order order = emissionOrderService.searchOrFail(orderCode);
        order.cancel();

        orderRepository.save(order);
    }

    @Transactional
    public void deliver(String orderCode) {
        Order order = emissionOrderService.searchOrFail(orderCode);
        order.deliver();
    }
}
