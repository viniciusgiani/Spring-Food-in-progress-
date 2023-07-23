package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.StateController;
import com.spring.data.api.v1.model.StateModel;
import com.spring.data.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class StateModelAssembler extends RepresentationModelAssemblerSupport<State, StateModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public StateModelAssembler() {
        super(StateController.class, StateModel.class);
    }

    @Override
    public StateModel toModel(State state) {
        StateModel stateModel = createModelWithId(state.getId(), state);
        modelMapper.map(state, stateModel);

        stateModel.add(foodLinks.linkToStates("states"));

        return stateModel;
    }

    @Override
    public CollectionModel<StateModel> toCollectionModel(Iterable<? extends State> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToStates());
    }
}
