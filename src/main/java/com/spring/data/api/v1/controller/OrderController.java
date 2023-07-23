package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.OrderInputDisassembler;
import com.spring.data.api.v1.assembler.OrderModelAssembler;
import com.spring.data.api.v1.assembler.OrderResumeModelAssembler;
import com.spring.data.api.v1.model.OrderModel;
import com.spring.data.api.v1.model.OrderResumeModel;
import com.spring.data.api.v1.model.input.OrderInput;
import com.spring.data.api.v1.openapi.controller.OrderControllerOpenApi;
import com.spring.data.core.data.PageWrapper;
import com.spring.data.core.data.PageableTranslator;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.exception.BusinessException;
import com.spring.data.domain.exception.EntityNotFoundException;
import com.spring.data.domain.filter.OrderFilter;
import com.spring.data.domain.model.Order;
import com.spring.data.domain.model.User;
import com.spring.data.domain.repository.OrderRepository;
import com.spring.data.domain.service.EmissionOrderService;
import com.spring.data.infrastructure.repository.specifications.OrderSpecs;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerOpenApi {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmissionOrderService emissionOrderService;

    @Autowired
    private OrderModelAssembler orderModelAssembler;

    @Autowired
    private OrderResumeModelAssembler orderResumeModelAssembler;

    @Autowired
    private OrderInputDisassembler orderInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Override
    @CheckSecurity.Orders.CanSearch
    @GetMapping
    public PagedModel<OrderResumeModel> search(OrderFilter filter,
                                               @PageableDefault(size = 10)Pageable pageable) {
        Pageable pageableTranslated = translatePageable(pageable);

        Page<Order> ordersPage = orderRepository.findAll(
                OrderSpecs.usingFilter(filter), pageableTranslated
        );

        ordersPage = new PageWrapper<>(ordersPage, pageable);

        return pagedResourcesAssembler.toModel(ordersPage, orderResumeModelAssembler);
    }

    @Override
    @CheckSecurity.Orders.CanCreate
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel add(@Valid @RequestBody OrderInput orderInput) {
        try {
            Order newOrder = orderInputDisassembler.toDomainObject(orderInput);

            newOrder.setClient(new User());
            newOrder.getClient().setId(1L);

            newOrder = emissionOrderService.emit(newOrder);

            return orderModelAssembler.toModel(newOrder);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Orders.CanGet
    @GetMapping("/{codeOrder}")
    public OrderModel search(@PathVariable String orderCode) {
        Order order = emissionOrderService.searchOrFail(orderCode);

        return orderModelAssembler.toModel(order);
    }

    private Pageable translatePageable(Pageable apiPageable) {
        var mapping = Map.of(
                "code", "code",
                "subtotal", "subtotal",
                "deliveryFee", "deliveryFee",
                "totalValue", "totalValue",
                "creationDate", "creationDate",
                "restaurant.id", "restaurant.id",
                "client.id", "client.id",
                "client.name", "client.name"
        );

        return PageableTranslator.translate(apiPageable, mapping);
    }
}
