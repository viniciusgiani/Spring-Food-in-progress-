package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.model.input.PaymentInput;
import com.spring.data.domain.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Payment toDomainObject(PaymentInput paymentInput) {
        return modelMapper.map(paymentInput, Payment.class);
    }

    public void copyToDomainObject(PaymentInput paymentInput, Payment payment) {
        modelMapper.map(paymentInput, payment);
    }
}
