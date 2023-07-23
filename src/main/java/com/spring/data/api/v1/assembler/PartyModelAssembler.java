package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.PartyController;
import com.spring.data.api.v1.model.PartyModel;
import com.spring.data.domain.model.Party;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PartyModelAssembler extends RepresentationModelAssemblerSupport<Party, PartyModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public PartyModelAssembler() {
        super(PartyController.class, PartyModel.class);
    }

    @Override
    public PartyModel toModel(Party party) {
        PartyModel partyModel = createModelWithId(party.getId(), party);
        modelMapper.map(party, partyModel);

        partyModel.add(foodLinks.linkToParties("parties"));

        partyModel.add(foodLinks.linkToPartyPermissions(party.getId(), "Permissions"));

        return partyModel;
    }

    @Override
    public CollectionModel<PartyModel> toCollectionModel(Iterable<? extends Party> entities) {
        return super.toCollectionModel(entities)
                .add(foodLinks.linkToParties());
    }
}
