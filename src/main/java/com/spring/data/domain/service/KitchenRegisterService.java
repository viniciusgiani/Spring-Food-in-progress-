package com.spring.data.domain.service;

import com.spring.data.domain.exception.EntityInUseException;
import com.spring.data.domain.exception.KitchenNotFoundException;
import com.spring.data.domain.model.Kitchen;
import com.spring.data.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KitchenRegisterService {

    private static final String MSG_KITCHEN_IN_USE
            = "State with code %d is in use";

    @Autowired
    private KitchenRepository kitchenRepository;

    @Transactional
    public Kitchen save(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    @Transactional
    public void delete(Long kitchenId) {
        try {
            kitchenRepository.deleteById(kitchenId);
            kitchenRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new KitchenNotFoundException(kitchenId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, kitchenId));
        }
    }

    public Kitchen searchOrFail(Long kitchenId) {
        return kitchenRepository.findById(kitchenId)
                .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    }
}
