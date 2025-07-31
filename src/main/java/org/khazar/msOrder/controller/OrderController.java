package org.khazar.msOrder.controller;

import lombok.RequiredArgsConstructor;
import org.khazar.msOrder.model.request.CreateOrderRequest;
import org.khazar.msOrder.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        orderService.createOrder(createOrderRequest);
    }
}
