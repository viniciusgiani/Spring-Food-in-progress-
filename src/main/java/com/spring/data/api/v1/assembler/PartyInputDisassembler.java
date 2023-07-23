package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.model.input.PartyInput;
import com.spring.data.domain.model.Party;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PartyInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Party toDomainObject(PartyInput partyInput) {
        return modelMapper.map(partyInput, Party.class);
    }

    public void copyToDomainObject(PartyInput partyInput, Party party) {
        modelMapper.map(partyInput, party);
    }
}
