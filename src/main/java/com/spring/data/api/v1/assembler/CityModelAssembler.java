package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.CityController;
import com.spring.data.api.v1.model.CityModel;
import com.spring.data.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City,CityModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public CityModelAssembler() {
        super(CityController.class, CityModel.class);
    }

    public CityModel toModel(City city) {
        CityModel cityModel = createModelWithId(city.getId(), city);
        modelMapper.map(city, cityModel);
        cityModel.add(foodLinks.linkToCities("cities"));
        cityModel.getState().add(foodLinks.linkToState(cityModel.getState().getId()));

        return cityModel;
    }

    public CollectionModel<CityModel> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToCities());
    }
}
