package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.FoodLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private FoodLinks foodLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(foodLinks.linkToKitchens("kitchens"));
        rootEntryPointModel.add(foodLinks.linkToOrders("orders"));
        rootEntryPointModel.add(foodLinks.linkToRestaurants("restaurants"));
        rootEntryPointModel.add(foodLinks.linkToParties("parties"));
        rootEntryPointModel.add(foodLinks.linkToUsers("users"));
        rootEntryPointModel.add(foodLinks.linkToPermissions("permissions"));
        rootEntryPointModel.add(foodLinks.linkToPayment("payments"));
        rootEntryPointModel.add(foodLinks.linkToStates("states"));
        rootEntryPointModel.add(foodLinks.linkToCities("cities"));
        rootEntryPointModel.add(foodLinks.linkToStatistics("statistics"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{}
}
