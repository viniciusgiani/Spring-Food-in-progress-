package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.assembler.ProductInputDisassembler;
import com.spring.data.api.v1.assembler.ProductModelAssembler;
import com.spring.data.api.v1.model.ProductModel;
import com.spring.data.api.v1.model.input.ProductInput;
import com.spring.data.api.v1.openapi.controller.RestaurantProductControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.Product;
import com.spring.data.domain.model.Restaurant;
import com.spring.data.domain.repository.ProductRepository;
import com.spring.data.domain.service.ProductRegisterService;
import com.spring.data.domain.service.RestaurantRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/products",
produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController implements RestaurantProductControllerOpenApi {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRegisterService productRegisterService;

    @Autowired
    private RestaurantRegisterService restaurantRegisterService;

    @Autowired
    ProductModelAssembler productModelAssembler;

    @Autowired
    private ProductInputDisassembler productInputDisassembler;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping
    public CollectionModel<ProductModel> list(@PathVariable Long restaurantId,
                                              @RequestParam(required = false, defaultValue = "false") Boolean includeNonactives) {
        Restaurant restaurant = restaurantRegisterService.searchOrFail(restaurantId);

        List<Product> allProducts = null;

        if (includeNonactives) {
            allProducts = productRepository.findAllByRestaurant(restaurant);
        } else {
            allProducts = productRepository.findActiveByRestaurant(restaurant);
        }

        return productModelAssembler.toCollectionModel(allProducts).add(foodLinks.linkToProducts(restaurantId));
    }

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping("/{productId}")
    public ProductModel search(@PathVariable Long restaurantId, @PathVariable Long productId) {
        Product product = productRegisterService.searchOrFail(restaurantId, productId);

        return productModelAssembler.toModel(product);
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel add(@PathVariable Long restaurantId,
                            @RequestBody @Valid ProductInput productInput) {

        Restaurant restaurant = restaurantRegisterService.searchOrFail(restaurantId);

        Product product = productInputDisassembler.toDomainObject(productInput);
        product.setRestaurant(restaurant);

        product = productRegisterService.save(product);

        return productModelAssembler.toModel(product);
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegister
    @PutMapping("/{productId}")
    public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId,
                               @RequestBody @Valid ProductInput productInput) {
        Product currentProduct = productRegisterService.searchOrFail(restaurantId, productId);

        productInputDisassembler.copyToDomainObject(productInput, currentProduct);

        currentProduct = productRegisterService.save(currentProduct);

        return productModelAssembler.toModel(currentProduct);
    }
}
