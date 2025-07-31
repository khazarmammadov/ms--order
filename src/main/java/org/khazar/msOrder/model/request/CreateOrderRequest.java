package org.khazar.msOrder.model.request;

import java.util.List;

public class CreateOrderRequest {

    private Long menuItemId;
    private List<OrderItemRequest> orderItems;
}
