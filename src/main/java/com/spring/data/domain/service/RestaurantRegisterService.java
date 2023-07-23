package com.spring.data.domain.service;

import com.spring.data.domain.exception.RestaurantNotFoundException;
import com.spring.data.domain.model.*;
import com.spring.data.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantRegisterService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRegisterService kitchenRegisterService;

    @Autowired
    private CityRegisterService cityRegisterService;

    @Autowired
    private PaymentRegisterService paymentRegisterService;

    @Autowired
    private UserRegisterService userRegisterService;


    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Long kitchenId = restaurant.getKitchen().getId();
        Long cityId = restaurant.getAddress().getCity().getId();

        Kitchen kitchen = kitchenRegisterService.searchOrFail(kitchenId);
        City city = cityRegisterService.searchOrFail(cityId);

        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void activate(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.activate();
    }

    @Transactional
    public void inactive(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.inactive();
    }

    @Transactional
    public void activate(List<Long> restaurantIds) {
        restaurantIds.forEach(this::activate);
    }

    @Transactional
    public void inactive(List<Long> restaurantIds) {
        restaurantIds.forEach(this::inactive);
    }

    @Transactional
    public void open(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.open();
    }

    @Transactional
    public void close(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.close();
    }

    @Transactional
    public void disassociatePaymentMethod(Long restaurantId, Long paymentMethodId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        Payment payment = paymentRegisterService.searchOrFail(paymentMethodId);

        restaurant.removePaymentMethod(payment);
    }

    @Transactional
    public void associatePaymentMethod(Long restaurantId, Long paymentMethodId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        Payment payment = paymentRegisterService.searchOrFail(paymentMethodId);

        restaurant.addPaymentMethod(payment);
    }

    @Transactional
    public void disassociateResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        User user = userRegisterService.searchOrFail(userId);

        restaurant.removeResponsible(user);
    }

    @Transactional
    public void associateResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        User user = userRegisterService.searchOrFail(userId);

        restaurant.addResponsible(user);
    }

    public Restaurant searchOrFail(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
}
