package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.RestaurantProductController;
import com.spring.data.api.v1.model.ProductModel;
import com.spring.data.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public ProductModelAssembler() {
        super(RestaurantProductController.class, ProductModel.class);
    }

    @Override
    public ProductModel toModel(Product product) {
        ProductModel productModel = createModelWithId(
                product.getId(), product, product.getRestaurant().getId()
        );

        modelMapper.map(product, productModel);

        productModel.add(foodLinks.linkToProducts(product.getRestaurant().getId(), "products"));

        productModel.add(foodLinks.linkToPhotoProduct(
                product.getRestaurant().getId(), product.getId(), "photo"
        ));

        return productModel;
    }
}
