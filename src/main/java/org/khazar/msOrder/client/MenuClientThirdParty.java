package org.khazar.msOrder.client;

import org.khazar.msOrder.model.client.MenuItemDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "http://localhost:8081/api/v1/menu-items")
public interface MenuClientThirdParty {

    @GetExchange("/{id}")
    MenuItemDto getMenuItemById(@PathVariable("id") Long id);

}