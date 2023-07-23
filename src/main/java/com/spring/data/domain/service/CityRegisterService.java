package com.spring.data.domain.service;

import com.spring.data.domain.exception.CityNotFoundException;
import com.spring.data.domain.exception.EntityInUseException;
import com.spring.data.domain.exception.EntityNotFoundException;
import com.spring.data.domain.model.City;
import com.spring.data.domain.model.State;
import com.spring.data.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityRegisterService {

    private static final String MSG_CITY_IN_USE
            = "City with code %d is in use";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRegisterService stateRegisterService;

    @Transactional
    public City save(City city) {

        Long id = city.getState().getId();

        State state = stateRegisterService.searchOrFail(id);

        city.setState(state);

        return cityRepository.save(city);
    }

    @Transactional
    public void delete(Long cityId) {
        try {
            cityRepository.deleteById(cityId);
            cityRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(cityId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_CITY_IN_USE, cityId)
            );
        }
    }

    public City searchOrFail(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }
}
