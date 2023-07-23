package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.StateInputDisassembler;
import com.spring.data.api.v1.assembler.StateModelAssembler;
import com.spring.data.api.v1.model.StateModel;
import com.spring.data.api.v1.model.input.StateInput;
import com.spring.data.api.v1.openapi.controller.StateControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.State;
import com.spring.data.domain.repository.StateRepository;
import com.spring.data.domain.service.StateRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerOpenApi {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private StateRegisterService stateRegisterService;

    @Autowired
    private StateModelAssembler stateModelAssembler;

    @Autowired
    private StateInputDisassembler stateInputDisassembler;

    @Override
    @CheckSecurity.States.CanQuery
    @GetMapping
    public CollectionModel<StateModel> list() {
        List<State> allStates = stateRepository.findAll();
        return stateModelAssembler.toCollectionModel(allStates);
    }

    @Override
    @CheckSecurity.States.CanQuery
    @GetMapping("/{stateId}")
    public StateModel search(@PathVariable Long stateId) {
        State state = stateRegisterService.searchOrFail(stateId);
        return stateModelAssembler.toModel(state);
    }

    @Override
    @CheckSecurity.States.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateModel add(@RequestBody @Valid StateInput stateInput) {
        State state = stateInputDisassembler.toDomainObject(stateInput);

        state = stateRegisterService.save(state);

        return stateModelAssembler.toModel(state);
    }

    @Override
    @CheckSecurity.States.CanEdit
    @PutMapping("/{stateId}")
    public StateModel update(@PathVariable Long stateId,
                                        @RequestBody @Valid StateInput stateInput) {
        State currentState = stateRegisterService.searchOrFail(stateId);

        stateInputDisassembler.copyToDomainObject(stateInput, currentState);

        currentState = stateRegisterService.save(currentState);

        return stateModelAssembler.toModel(currentState);
    }

    @Override
    @CheckSecurity.States.CanEdit
    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long stateId) {
        stateRegisterService.delete(stateId);
        return ResponseEntity.noContent().build();
    }
}
