package org.khazar.msOrder.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.khazar.msOrder.model.enums.OrderStatus;
import org.khazar.msOrder.model.request.OrderItemRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long restaurantId;
    private List<OrderItemRequest> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
}