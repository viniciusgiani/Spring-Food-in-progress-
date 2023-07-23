package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.assembler.PermissionModelAssembler;
import com.spring.data.api.v1.model.PermissionModel;
import com.spring.data.api.v1.openapi.controller.PartyPermissionControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.Party;
import com.spring.data.domain.service.PartyRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/parties/{partyId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartyPermissionController implements PartyPermissionControllerOpenApi {

    @Autowired
    private PartyRegisterService partyRegisterService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @CheckSecurity.UsersPartyPermissions.CanQuery
    @GetMapping
    public CollectionModel<PermissionModel> list(@PathVariable Long partyId) {
        Party party = partyRegisterService.searchOrFail(partyId);

        CollectionModel<PermissionModel> permissionsModel
                = permissionModelAssembler.toCollectionModel(party.getPermissions())
                .removeLinks()
                .add(foodLinks.linkToPartyPermissions(partyId))
                .add(foodLinks.linkToPartyPermissionAssociation(partyId, "associate"));

        permissionsModel.getContent().forEach(permissionModel -> {
            permissionsModel.add(foodLinks.linkToPartyPermissionDissociation(
                    partyId, permissionModel.getId(), "dissociate"
            ));
        });
        return permissionsModel;
        }

    @Override
    @CheckSecurity.UsersPartyPermissions.CanEdit
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> dissociate(@PathVariable Long partyId, @PathVariable Long permissionId) {
        partyRegisterService.dissociatePermission(partyId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.UsersPartyPermissions.CanEdit
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long partyId, @PathVariable Long permissionId) {
        partyRegisterService.associatePermission(partyId, permissionId);
        return ResponseEntity.noContent().build();
    }
}
