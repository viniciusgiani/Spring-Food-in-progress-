package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.openapi.controller.FlowOrderControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.service.FlowOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlowOrderController implements FlowOrderControllerOpenApi {

    @Autowired
    private FlowOrderService flowOrderService;

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirm(@PathVariable String orderCode) {
        flowOrderService.confirm(orderCode);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancel(@PathVariable String orderCode) {
        flowOrderService.cancel(orderCode);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deliver(@PathVariable String orderCode) {
        flowOrderService.deliver(orderCode);
        return ResponseEntity.noContent().build();
    }
}
