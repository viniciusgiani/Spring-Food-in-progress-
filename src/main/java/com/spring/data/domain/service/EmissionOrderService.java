package com.spring.data.domain.service;

import com.spring.data.domain.exception.BusinessException;
import com.spring.data.domain.exception.OrderNotFoundException;
import com.spring.data.domain.model.*;
import com.spring.data.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissionOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRegisterService restaurantRegisterService;

    @Autowired
    private CityRegisterService cityRegisterService;

    @Autowired
    private  UserRegisterService userRegisterService;

    @Autowired
    private ProductRegisterService productRegisterService;

    @Autowired
    private PaymentRegisterService paymentRegisterService;

    @Transactional
    public Order emit(Order order) {
        validateOrder(order);
        validateItems(order);

        order.setDeliveryFee(order.getRestaurant().getDeliveryFee());
        order.getTotalValue();

        return orderRepository.save(order);
    }

    private void validateOrder(Order order) {
        City city = cityRegisterService.searchOrFail(order.getAddress().getCity().getId());
        User client = userRegisterService.searchOrFail(order.getClient().getId());
        Restaurant restaurant = restaurantRegisterService.searchOrFail(order.getRestaurant().getId());
        Payment payment = paymentRegisterService.searchOrFail(order.getPayment().getId());

        order.getAddress().setCity(city);
        order.setClient(client);
        order.setRestaurant(restaurant);
        order.setPayment(payment);

        if(restaurant.acceptPaymentMethod(payment)) {
            throw new BusinessException(String.format("Payment method '%s' is not accepted", payment.getDescription()));
        }
    }

    private void validateItems(Order order) {
        order.getItems().forEach(itemOrder -> {
            Product product = productRegisterService.searchOrFail(
                    order.getRestaurant().getId(), itemOrder.getProduct().getId()
            );
            itemOrder.setOrder(order);
            itemOrder.setProduct(product);
            itemOrder.setUnitPrice(product.getPrice());
        });
    }

    public Order searchOrFail(String orderCode) {
        return orderRepository.findByCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException(orderCode));
    }

}
