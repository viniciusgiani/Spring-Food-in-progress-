package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.KitchenInputDisassembler;
import com.spring.data.api.v1.assembler.KitchenModelAssembler;
import com.spring.data.api.v1.model.KitchenModel;
import com.spring.data.api.v1.model.input.KitchenInput;
import com.spring.data.api.v1.openapi.controller.KitchenControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.Kitchen;
import com.spring.data.domain.repository.KitchenRepository;
import com.spring.data.domain.service.KitchenRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/v1/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController implements KitchenControllerOpenApi {
    @Autowired
    KitchenRepository kitchenRepository;

    @Autowired
    KitchenRegisterService kitchenRegisterService;

    @Autowired
    private KitchenInputDisassembler kitchenInputDisassembler;

    @Autowired
    private KitchenModelAssembler kitchenModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @Override
    @CheckSecurity.Kitchens.CanQuery
    @GetMapping
    public PagedModel<KitchenModel> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<Kitchen> kitchensPage = kitchenRepository.findAll(pageable);

        PagedModel<KitchenModel> kitchenPagedModel = pagedResourcesAssembler.toModel(kitchensPage, kitchenModelAssembler);

        return kitchenPagedModel;
    }

    @Override
    @CheckSecurity.Kitchens.CanQuery
    @GetMapping("/{kitchenId}")
    public KitchenModel search(@PathVariable Long kitchenId) {
        Kitchen kitchen = kitchenRegisterService.searchOrFail(kitchenId);
        return kitchenModelAssembler.toModel(kitchen);
    }

    @CheckSecurity.Kitchens.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel add(@RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);
        kitchen = kitchenRepository.save(kitchen);
        return kitchenModelAssembler.toModel(kitchen);
    }

    @Override
    @CheckSecurity.Kitchens.CanEdit
    @PutMapping("/{kitchenId}")
    public KitchenModel update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen currentKitchen = kitchenRegisterService.searchOrFail(kitchenId);
        kitchenInputDisassembler.copyToDomainObject(kitchenInput, currentKitchen);
        currentKitchen = kitchenRegisterService.save(currentKitchen);
        return kitchenModelAssembler.toModel(currentKitchen);
    }

    @Override
    @CheckSecurity.Kitchens.CanEdit
    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long kitchenId) {
            kitchenRegisterService.delete(kitchenId);
            return ResponseEntity.noContent().build();
    }
}
