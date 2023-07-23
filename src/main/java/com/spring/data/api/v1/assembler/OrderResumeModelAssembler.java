package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.OrderController;
import com.spring.data.api.v1.model.OrderResumeModel;
import com.spring.data.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderResumeModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderResumeModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public OrderResumeModelAssembler() {
        super(OrderController.class, OrderResumeModel.class);
    }

    @Override
    public OrderResumeModel toModel(Order order) {
        OrderResumeModel orderResumeModel = createModelWithId(order.getCode(), order);

        modelMapper.map(order, orderResumeModel);

        orderResumeModel.add(foodLinks.linkToOrders("orders"));

        orderResumeModel.getRestaurant().add(
                foodLinks.linkToRestaurant(order.getRestaurant().getId())
        );

        orderResumeModel.getClient().add(foodLinks.linkToUser(order.getClient().getId()));

        return orderResumeModel;
    }
}
