package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.RestaurantBasicModelAssembler;
import com.spring.data.api.v1.assembler.RestaurantInputDisassembler;
import com.spring.data.api.v1.assembler.RestaurantModelAssembler;
import com.spring.data.api.v1.assembler.RestaurantOnlyNameModelAssembler;
import com.spring.data.api.v1.model.RestaurantBasicModel;
import com.spring.data.api.v1.model.RestaurantModel;
import com.spring.data.api.v1.model.RestaurantOnlyNameModel;
import com.spring.data.api.v1.model.input.RestaurantInput;
import com.spring.data.api.v1.openapi.controller.RestaurantControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.exception.*;
import com.spring.data.domain.model.Restaurant;
import com.spring.data.domain.repository.RestaurantRepository;
import com.spring.data.domain.service.RestaurantRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerOpenApi {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantRegisterService restaurantRegisterService;

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @Autowired
    private RestaurantBasicModelAssembler restaurantBasicModelAssembler;

    @Autowired
    private RestaurantOnlyNameModelAssembler restaurantOnlyNameModelAssembler;

    @Autowired
    private RestaurantInputDisassembler restaurantInputDisassembler;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping
    public CollectionModel<RestaurantBasicModel> list() {
        return restaurantBasicModelAssembler
                .toCollectionModel(restaurantRepository.findAll());
    }

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping(params = "projection=only-name")
    public CollectionModel<RestaurantOnlyNameModel> listOnlyName() {
        return restaurantOnlyNameModelAssembler
                .toCollectionModel(restaurantRepository.findAll());
    }

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping("/{restaurantId}")
    public RestaurantModel search(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegisterService.searchOrFail(restaurantId);

        return restaurantModelAssembler.toModel(restaurant);
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel add(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);
            return restaurantModelAssembler.toModel(restaurantRegisterService.save(restaurant));
        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/{restaurantId}")
    public RestaurantModel update(@PathVariable Long restaurantId,
                                    @RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant currentRestaurant = restaurantRegisterService.searchOrFail(restaurantId);
            restaurantInputDisassembler.copyToDomainObject(restaurantInput, currentRestaurant);
            return restaurantModelAssembler.toModel(restaurantRegisterService.save(currentRestaurant));
        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activate(@PathVariable Long restaurantId) {
        restaurantRegisterService.activate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactivate(@PathVariable Long restaurantId) {
        restaurantRegisterService.inactive(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            restaurantRegisterService.activate(restaurantIds);
            return ResponseEntity.noContent().build();
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @DeleteMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactivateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            restaurantRegisterService.inactive(restaurantIds);
            return ResponseEntity.noContent().build();
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> open(@PathVariable Long restaurantId) {
        restaurantRegisterService.open(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> close(@PathVariable Long restaurantId) {
        restaurantRegisterService.close(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
