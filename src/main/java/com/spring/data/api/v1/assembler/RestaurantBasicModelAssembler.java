package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.RestaurantController;
import com.spring.data.api.v1.model.RestaurantBasicModel;
import com.spring.data.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBasicModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantBasicModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public RestaurantBasicModelAssembler() {
        super(RestaurantController.class, RestaurantBasicModel.class);
    }

    @Override
    public RestaurantBasicModel toModel(Restaurant restaurant) {
        RestaurantBasicModel restaurantBasicModel = createModelWithId(
                restaurant.getId(), restaurant
        );

        modelMapper.map(restaurant, restaurantBasicModel);

        restaurantBasicModel.add(foodLinks.linkToRestaurants("restaurants"));

        restaurantBasicModel.getKitchen().add(
                foodLinks.linkToKitchen(restaurant.getKitchen().getId())
        );

        return restaurantBasicModel;
    }

    @Override
    public CollectionModel<RestaurantBasicModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToRestaurants());
    }
}
