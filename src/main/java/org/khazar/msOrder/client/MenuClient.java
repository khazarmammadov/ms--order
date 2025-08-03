package org.khazar.msOrder.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.khazar.msOrder.exception.ClientException;
import org.khazar.msOrder.exception.ErrorResponse;
import org.khazar.msOrder.model.client.MenuItemDto;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpStatusCodeException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuClient {

    private final ObjectMapper objectMapper;

    private final MenuClientThirdParty  menuClientThirdParty;

    @SneakyThrows
    public MenuItemDto getMenuItemById(Long id) {
        log.info("ActionLog.MenuClient.getMenuItemById.start - id: {}", id);

        try {
            var menuItem = menuClientThirdParty.getMenuItemById(id);

            log.info("ActionLog.MenuClient.getMenuItemById.success - id: {}, menuItem: {}", id, menuItem);
            return menuItem;
        } catch (HttpStatusCodeException exception) {
            log.error("ActionLog.MenuClient.getMenuItemById.error - id: {}", id);
            var errorResponse = objectMapper.readValue(exception.getResponseBodyAsString(), ErrorResponse.class);

            throw new ClientException(
                    errorResponse.getCode(),
                    errorResponse.getMessage(),
                    exception.getStatusCode().value()
            );
        }
    }
}
