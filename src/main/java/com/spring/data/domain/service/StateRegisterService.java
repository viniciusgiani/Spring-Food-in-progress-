package com.spring.data.domain.service;

import com.spring.data.domain.exception.EntityInUseException;
import com.spring.data.domain.exception.StateNotFoundException;
import com.spring.data.domain.model.State;
import com.spring.data.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StateRegisterService {

    private static final String MSG_STATE_IN_USE
            = "State with code %d is in use";

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public State save(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public void delete(Long stateId) {
        try {
            stateRepository.deleteById(stateId);
            stateRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(stateId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_STATE_IN_USE, stateId)
            );
        }
    }

    public State searchOrFail(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new StateNotFoundException(stateId));
    }
}
