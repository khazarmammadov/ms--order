package org.khazar.msOrder.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khazar.msOrder.model.request.CreateOrderRequest;
import org.khazar.msOrder.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest) {

    }

    @Override
    public void confirmOrder(Long orderId) {

    }
}
