package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.RestaurantController;
import com.spring.data.api.v1.model.RestaurantModel;
import com.spring.data.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class RestaurantModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FoodLinks foodLinks;

    public RestaurantModelAssembler() {
        super(RestaurantController.class, RestaurantModel.class);
    }

    @Override
    public RestaurantModel toModel(Restaurant restaurant) {
        RestaurantModel restaurantModel = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantModel);

        restaurantModel.add(foodLinks.linkToRestaurants("restaurants"));

        if (restaurant.activatePermitted()) {
            restaurantModel.add(
                    foodLinks.linkToRestaurantActivate(restaurant.getId(), "activate")
            );
        }
        if (restaurant.inactivePermitted()) {
            restaurantModel.add(
                    foodLinks.linkToRestaurantInactivate(restaurant.getId(), "inactivate")
            );
        }
        if (restaurant.openPermitted()) {
            restaurantModel.add(
                    foodLinks.linkToRestaurantOpening(restaurant.getId(), "open")
            );
        }
        if (restaurant.closePermitted()) {
            restaurantModel.add(
                    foodLinks.linkToRestaurantClosing(restaurant.getId(), "close")
            );
        }
        restaurantModel.add(foodLinks.linkToProducts(restaurant.getId(), "products"));

        restaurantModel.getKitchen().add(
                foodLinks.linkToKitchen(restaurant.getKitchen().getId())
        );

        if (restaurantModel.getAddress() != null && restaurantModel.getAddress().getCity() != null) {
                restaurantModel.getAddress().getCity().add(
                foodLinks.linkToCity(restaurant.getAddress().getCity().getId())
        );
        }

        restaurantModel.add(foodLinks.linkToRestaurantPayment(restaurant.getId(),
                "payment-methods"));

        restaurantModel.add(foodLinks.linkToRestaurantResponsible(restaurant.getId(),"responsible"));

        return restaurantModel;
    }

    @Override
    public CollectionModel<RestaurantModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToRestaurants());
    }
}
