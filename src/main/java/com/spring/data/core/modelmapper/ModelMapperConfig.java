package com.spring.data.core.modelmapper;

import com.spring.data.api.v1.model.AddressModel;
import com.spring.data.api.v1.model.input.ItemOrderInput;
import com.spring.data.domain.model.Address;
import com.spring.data.domain.model.ItemOrder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemOrderInput.class, ItemOrder.class)
                .addMappings(mapper -> mapper.skip(ItemOrder::setId));

        var addressToAddressModelTypeMap = modelMapper.createTypeMap(
                Address.class, AddressModel.class
        );

        addressToAddressModelTypeMap.<String>addMapping(
                addressSrc -> addressSrc.getCity().getState().getName(),
                (addressModelDest, value) -> addressModelDest.getCity().setState(value)
        );

        return modelMapper;
    }
}
