package org.khazar.msOrder.service;

import org.khazar.msOrder.model.request.CreateOrderRequest;

public interface OrderService {

    void createOrder(CreateOrderRequest createOrderRequest);

    void confirmOrder(Long orderId);
}
