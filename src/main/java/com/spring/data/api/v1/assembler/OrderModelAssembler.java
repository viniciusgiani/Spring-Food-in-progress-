package com.spring.data.api.v1.assembler;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.controller.OrderController;
import com.spring.data.api.v1.model.OrderModel;
import com.spring.data.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public OrderModelAssembler() {
        super(OrderController.class, OrderModel.class);
    }

    @Override
    public OrderModel toModel(Order order) {
        OrderModel orderModel = createModelWithId(order.getCode(), order);
        modelMapper.map(order, orderModel);

        orderModel.add(foodLinks.linkToOrders("orders"));

        if (order.canBeConfirmed()) {
            orderModel.add(foodLinks.linkToConfirmationOrder(order.getCode(), "confirm"));
        }

        if(order.canBeCanceled()) {
            orderModel.add(foodLinks.linkToCanceledOrder(order.getCode(),"cancel"));
        }

        if (order.canBeDelivered()) {
            orderModel.add(foodLinks.linkToDeliverOrder(order.getCode(), "deliver"));
        }

        orderModel.getRestaurant().add(
                foodLinks.linkToRestaurant(order.getRestaurant().getId())
        );

        orderModel.getClient().add(
                foodLinks.linkToUser(order.getClient().getId())
        );

        orderModel.getPaymentModel().add(
                foodLinks.linkToPayment(order.getPayment().getId())
        );

        orderModel.getAddressDelivery().getCity().add(
                foodLinks.linkToCity(order.getAddress().getCity().getId())
        );

        orderModel.getItems().forEach(itemOrderModel -> {
            foodLinks.linkToProduct(
                    orderModel.getRestaurant().getId(), itemOrderModel.getProductId()
            );
        });
        return orderModel;
    }
}
