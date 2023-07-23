package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.assembler.PaymentModelAssembler;
import com.spring.data.api.v1.model.PaymentModel;
import com.spring.data.api.v1.openapi.controller.RestaurantPaymentControllerOpenApi;
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
@RequestMapping(path = "/v1/restaurants/{restaurantId}/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentController implements RestaurantPaymentControllerOpenApi {

    @Autowired
    private RestaurantRegisterService restaurantRegisterService;

    @Autowired
    private PaymentModelAssembler paymentModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping
    public CollectionModel<PaymentModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegisterService.searchOrFail(restaurantId);

        CollectionModel<PaymentModel> paymentModels = paymentModelAssembler.toCollectionModel(restaurant.getPayments())
                .removeLinks()
                .add(foodLinks.linkToPayment(restaurantId))
                .add(foodLinks.linkToRestaurantPaymentAssociation(
                        restaurantId, "associate"
                ));
        paymentModels.getContent().forEach(paymentModel -> {
            paymentModel.add(foodLinks.linkToRestaurantPaymentDissociation(
                    restaurantId, paymentModel.getId(), "dissociate"
            ));
        });
        return paymentModels;
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> dissociate(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
        restaurantRegisterService.disassociatePaymentMethod(restaurantId, paymentId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
        restaurantRegisterService.associatePaymentMethod(restaurantId, restaurantId);

        return ResponseEntity.noContent().build();
    }
}
