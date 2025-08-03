package org.khazar.msOrder.mapper;

import lombok.experimental.UtilityClass;
import org.khazar.msOrder.dao.entity.OrderEntity;
import org.khazar.msOrder.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Map;

@UtilityClass
public final class OrderMapper {

    public OrderEntity toOrderEntity(Long restaurantId, String itemsJson, BigDecimal total) {
        return OrderEntity.builder()
                .restaurantId(restaurantId)
                .itemsJson(itemsJson)
                .totalAmount(total)
                .status(OrderStatus.NEW)
                .build();
    }

    public static Map<String, Object> buildItemDetails(Long menuItemId, String name,
                                                       BigDecimal price, Integer quantity) {
        return Map.of(
                "id", menuItemId,
                "name", name,
                "price", price,
                "quantity", quantity
        );
    }
}
