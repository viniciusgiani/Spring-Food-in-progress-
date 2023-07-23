package com.spring.data.domain.service;

import com.spring.data.domain.exception.EntityInUseException;
import com.spring.data.domain.exception.PartyNotFoundException;
import com.spring.data.domain.model.Party;
import com.spring.data.domain.model.Permission;
import com.spring.data.domain.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartyRegisterService {

    private static final String MSG_PARTY_IN_USE =
            "Party of code %d is in use";

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PermissionRegisterService permissionRegisterService;

    @Transactional
    public Party save(Party party) {
        return partyRepository.save(party);
    }

    @Transactional
    public void delete(Long partyId) {
        try {
            partyRepository.deleteById(partyId);
            partyRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PartyNotFoundException(partyId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_PARTY_IN_USE, partyId)
            );
        }
    }

    @Transactional
    public void dissociatePermission(Long partyId, Long permissionId) {
        Party party = searchOrFail(partyId);
        Permission permission = permissionRegisterService.searchOrFail(permissionId);

        party.addPermission(permission);
    }

    @Transactional
    public void associatePermission(Long partyId, Long permissionId) {
    Party party = searchOrFail(partyId);
    Permission permission = permissionRegisterService.searchOrFail(permissionId);

    party.addPermission(permission);
    }

    public Party searchOrFail(Long partyId) {
        return partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException(partyId));
    }
}
