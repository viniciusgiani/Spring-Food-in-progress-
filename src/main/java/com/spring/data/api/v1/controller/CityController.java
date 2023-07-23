package com.spring.data.api.v1.controller;

import com.spring.data.api.ResourceUriHelper;
import com.spring.data.api.v1.assembler.CityInputDisassembler;
import com.spring.data.api.v1.assembler.CityModelAssembler;
import com.spring.data.api.v1.model.CityModel;
import com.spring.data.api.v1.model.input.CityInput;
import com.spring.data.api.v1.openapi.controller.CityControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.exception.BusinessException;
import com.spring.data.domain.exception.EntityNotFoundException;
import com.spring.data.domain.model.City;
import com.spring.data.domain.repository.CityRepository;
import com.spring.data.domain.service.CityRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/v1/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityRegisterService cityRegisterService;

    @Autowired
    private CityModelAssembler cityModelAssembler;

    @Autowired
    private CityInputDisassembler cityInputDisassembler;

    @Override
    @CheckSecurity.Cities.CanQuery
    @GetMapping
    public CollectionModel<CityModel> list() {
        List<City> allCities = cityRepository.findAll();

        return cityModelAssembler.toCollectionModel(allCities);
    }

    @Override
    @CheckSecurity.Cities.CanQuery
    @GetMapping("/{cityId}")
    public CityModel search(@PathVariable Long cityId) {
        City city = cityRegisterService.searchOrFail(cityId);

        return cityModelAssembler.toModel(city);
    }

    @Override
    @CheckSecurity.Cities.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel add(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDisassembler.toDomainObject(cityInput);
            city = cityRegisterService.save(city);
            CityModel cityModel = cityModelAssembler.toModel(city);
            ResourceUriHelper.addUriInResponseHeader(cityModel.getId());

            return cityModel;
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Cities.CanEdit
    @PutMapping("/{cityId}")
    public CityModel update(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
        try {
            City currentCity = cityRegisterService.searchOrFail(cityId);
            cityInputDisassembler.copyToDomainObject(cityInput, currentCity);
            currentCity = cityRegisterService.save(currentCity);
            return cityModelAssembler.toModel(currentCity);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Cities.CanEdit
    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long cityId) {
        cityRegisterService.delete(cityId);
        return ResponseEntity.noContent().build();
    }

}
