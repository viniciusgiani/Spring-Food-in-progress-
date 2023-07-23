package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.model.input.RestaurantInput;
import com.spring.data.domain.model.City;
import com.spring.data.domain.model.Kitchen;
import com.spring.data.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurant toDomainObject(RestaurantInput restaurantInput) {
        return modelMapper.map(restaurantInput, Restaurant.class);
    }

    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
        restaurant.setKitchen(new Kitchen());

        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(new City());
        }

        modelMapper.map(restaurantInput, restaurant);
    }
}
