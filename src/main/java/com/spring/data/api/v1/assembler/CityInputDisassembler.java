package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.model.input.CityInput;
import com.spring.data.domain.model.City;
import com.spring.data.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CityInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomainObject(CityInput cityInput) {
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInput cityInput, City city) {
        city.setState(new State());

        modelMapper.map(cityInput, city);
    }
}
