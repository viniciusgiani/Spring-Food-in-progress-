package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.assembler.PartyModelAssembler;
import com.spring.data.api.v1.model.PartyModel;
import com.spring.data.api.v1.openapi.controller.UserPartyControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.User;
import com.spring.data.domain.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users/{userId}/parties", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPartyController implements UserPartyControllerOpenApi {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private PartyModelAssembler partyModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @CheckSecurity.UsersPartyPermissions.CanQuery
    @GetMapping
    public CollectionModel<PartyModel> list(@PathVariable Long userId) {
        User user = userRegisterService.searchOrFail(userId);

        CollectionModel<PartyModel> partyModels = partyModelAssembler.toCollectionModel(user.getParties())
                .removeLinks()
                .add(foodLinks.linkToUserPartyAssociation(userId, "associate"));

        partyModels.getContent().forEach(partyModel -> {
            partyModel.add(foodLinks.linkToUserPartyDissociation(
                    userId, partyModel.getId(), "dissociate"
            ));
        });
        return partyModels;
    }

    @Override
    @CheckSecurity.UsersPartyPermissions.CanEdit
    @PutMapping("/{partyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long partyId) {
        userRegisterService.associateParty(userId, partyId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.UsersPartyPermissions.CanEdit
    @DeleteMapping("/{partyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> dissociate(@PathVariable Long userId,
                                               @PathVariable Long partyId) {
        userRegisterService.dissociateParty(userId, partyId);

        return ResponseEntity.noContent().build();
    }
}
