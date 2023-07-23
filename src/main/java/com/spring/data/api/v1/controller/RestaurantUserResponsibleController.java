package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.assembler.UserModelAssembler;
import com.spring.data.api.v1.model.UserModel;
import com.spring.data.api.v1.openapi.controller.RestaurantUserResponsibleControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.Restaurant;
import com.spring.data.domain.service.RestaurantRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserResponsibleController implements RestaurantUserResponsibleControllerOpenApi {

    @Autowired
    private RestaurantRegisterService registerRestaurant;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @GetMapping
    public CollectionModel<UserModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = registerRestaurant.searchOrFail(restaurantId);

        CollectionModel<UserModel> userModels = userModelAssembler
                .toCollectionModel(restaurant.getResponsible())
                .removeLinks()
                .add(foodLinks.linkToRestaurantResponsible(restaurantId))
                .add(foodLinks.linkToRestaurantResponsibleAssociation(restaurantId, "associate"));

        userModels.getContent().stream().forEach(userModel -> {
            userModel.add(foodLinks.linkToRestaurantResponsibleDissociation(
                    restaurantId,userModel.getId(), "disassociate"
            ));
        });

        return userModels;
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registerRestaurant.associateResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @DeleteMapping("/{UserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> dissociate(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registerRestaurant.disassociateResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }
}
