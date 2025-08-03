package org.khazar.msOrder.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khazar.msOrder.client.MenuClient;
import org.khazar.msOrder.exception.NotFoundException;
import org.khazar.msOrder.helper.KafkaHelper;
import org.khazar.msOrder.mapper.OrderMapper;
import org.khazar.msOrder.model.dto.OrderConfirmedEvent;
import org.khazar.msOrder.model.dto.OrderCreatedEvent;
import org.khazar.msOrder.model.enums.OrderStatus;
import org.khazar.msOrder.model.request.CreateOrderRequest;
import org.khazar.msOrder.model.request.OrderItemRequest;
import org.khazar.msOrder.repository.OrderRepository;
import org.khazar.msOrder.service.OrderService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final MenuClient menuClient;
    private final OrderRepository orderRepository;
    private final KafkaHelper kafkaHelper;

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest) {
        log.info("ActionLog.createOrder.start - request: {}", createOrderRequest);
        var total = BigDecimal.ZERO;

        List<Map<String, Object>> itemDetails = new ArrayList<>();

        for (OrderItemRequest item : createOrderRequest.getOrderItems()) {
            var menuItem = menuClient.getMenuItemById(item.getMenuItemId());
            var price = menuItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(price);

            var stringObjectMap = OrderMapper.buildItemDetails(
                    item.getMenuItemId(),
                    menuItem.getName(),
                    price,
                    item.getQuantity()
            );

            itemDetails.add(stringObjectMap);
        }

        String itemsJson = new Gson().toJson(itemDetails);

        var orderEntity = OrderMapper.toOrderEntity(createOrderRequest.getRestaurantId(), itemsJson, total);

        var savedEntity = orderRepository.save(orderEntity);

        var orderCreatedEvent = new OrderCreatedEvent(
                savedEntity.getId(),
                savedEntity.getRestaurantId(),
                savedEntity.getTotalAmount()
        );

        kafkaHelper.send("orderCreated", orderCreatedEvent);

        log.info("ActionLog.createOrder.start - response: {}", orderCreatedEvent);


    }

    @Override
    public void confirmOrder(Long orderId) {
        log.info("ActionLog.confirmOrder.start - orderId: {}", orderId);
        orderRepository.findById(orderId)
                .ifPresentOrElse(orderEntity -> {
                    orderEntity.setStatus(OrderStatus.CONFIRMED);
                    orderRepository.save(orderEntity);
                    log.info("ActionLog.confirmOrder.start - response: {}", orderEntity);
                    kafkaHelper.send("orderConfirmed", new OrderConfirmedEvent(
                            orderEntity.getId(),
                            orderEntity.getRestaurantId()
                    ));
                    log.info("ActionLog.confirmOrder.start - response: {}", orderEntity);
                }, () -> {
                    log.info("ActionLog.confirmOrder.start - error");
                    throw new NotFoundException("Order not found" + orderId);

                });
    }
}
