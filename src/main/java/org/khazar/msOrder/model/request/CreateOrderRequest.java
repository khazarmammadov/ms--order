package org.khazar.msOrder.model.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private Long restaurantId;
    private List<OrderItemRequest> orderItems;
}
