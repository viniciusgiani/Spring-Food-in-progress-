package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.PartyInputDisassembler;
import com.spring.data.api.v1.assembler.PartyModelAssembler;
import com.spring.data.api.v1.model.PartyModel;
import com.spring.data.api.v1.model.input.PartyInput;
import com.spring.data.api.v1.openapi.controller.PartyControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.Party;
import com.spring.data.domain.repository.PartyRepository;
import com.spring.data.domain.service.PartyRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/parties", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartyController implements PartyControllerOpenApi {
    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyRegisterService partyRegisterService;

    @Autowired
    private PartyModelAssembler partyModelAssembler;

    @Autowired
    private PartyInputDisassembler partyInputDisassembler;

    @Override
    @CheckSecurity.Payment.CanQuery
    @GetMapping
    public CollectionModel<PartyModel> list() {
        List<Party> allParties = partyRepository.findAll();

        return partyModelAssembler.toCollectionModel(allParties);
    }

    @Override
    @CheckSecurity.Payment.CanQuery
    @GetMapping("/{partyId}")
    public PartyModel search(@PathVariable Long partyId) {
        Party party = partyRegisterService.searchOrFail(partyId);

        return partyModelAssembler.toModel(party);
    }

    @Override
    @CheckSecurity.Payment.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartyModel add(@RequestBody @Valid PartyInput partyInput) {
        Party party = partyInputDisassembler.toDomainObject(partyInput);

        party = partyRegisterService.save(party);

        return partyModelAssembler.toModel(party);
    }

    @Override
    @CheckSecurity.Payment.CanEdit
    @PutMapping("/{partyId}")
    public PartyModel update(@PathVariable Long partyId,
                             @RequestBody @Valid PartyInput partyInput) {
        Party currentParty = partyRegisterService.searchOrFail(partyId);

        partyInputDisassembler.copyToDomainObject(partyInput, currentParty);

        currentParty = partyRegisterService.save(currentParty);

        return partyModelAssembler.toModel(currentParty);
    }

    @Override
    @CheckSecurity.Payment.CanEdit
    @DeleteMapping("/{partyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long partyId) {
        partyRegisterService.delete(partyId);
        return ResponseEntity.noContent().build();
    }
}
