package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.model.input.ProductInput;
import com.spring.data.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Product toDomainObject(ProductInput productInput) {
        return modelMapper.map(productInput, Product.class);
    }

    public void copyToDomainObject(ProductInput productInput, Product product) {
        modelMapper.map(productInput, product);
    }
}
