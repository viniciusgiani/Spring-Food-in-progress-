package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.RestaurantController;
import com.spring.data.api.v1.model.RestaurantOnlyNameModel;
import com.spring.data.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOnlyNameModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOnlyNameModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public RestaurantOnlyNameModelAssembler() {
        super(RestaurantController.class, RestaurantOnlyNameModel.class);
    }

    @Override
    public RestaurantOnlyNameModel toModel(Restaurant restaurant) {
        RestaurantOnlyNameModel restaurantOnlyNameModel = createModelWithId(
                restaurant.getId(), restaurant
        );

        modelMapper.map(restaurant, restaurantOnlyNameModel);

        restaurantOnlyNameModel.add(foodLinks.linkToRestaurants("restaurants"));

        return restaurantOnlyNameModel;
    }

    @Override
    public CollectionModel<RestaurantOnlyNameModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToRestaurants());
    }

}
