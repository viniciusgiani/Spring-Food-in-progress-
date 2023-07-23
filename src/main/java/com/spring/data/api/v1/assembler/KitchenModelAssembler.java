package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.KitchenController;
import com.spring.data.api.v1.model.KitchenModel;
import com.spring.data.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class KitchenModelAssembler extends RepresentationModelAssemblerSupport<Kitchen, KitchenModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public KitchenModelAssembler() {
        super(KitchenController.class, KitchenModel.class);
    }

    @Override
    public KitchenModel toModel(Kitchen kitchen) {
        KitchenModel kitchenModel = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, kitchenModel);

        kitchenModel.add(foodLinks.linkToKitchens("kitchens"));

        return kitchenModel;
    }
}
