package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.PaymentController;
import com.spring.data.api.v1.model.PaymentModel;
import com.spring.data.domain.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PaymentModelAssembler extends RepresentationModelAssemblerSupport<Payment, PaymentModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public PaymentModelAssembler() {
        super(PaymentController.class, PaymentModel.class);
    }

    @Override
    public PaymentModel toModel(Payment payment) {
        PaymentModel paymentModel =
                createModelWithId(payment.getId(), payment);

        modelMapper.map(payment, paymentModel);

        paymentModel.add(foodLinks.linkToPayment("payments"));

        return paymentModel;
    }

    @Override
    public CollectionModel<PaymentModel> toCollectionModel(Iterable<? extends Payment> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToPayment());
    }
}
