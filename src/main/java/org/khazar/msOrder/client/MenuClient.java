package org.khazar.msOrder.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.khazar.msOrder.exception.ClientException;
import org.khazar.msOrder.exception.ErrorResponse;
import org.khazar.msOrder.model.client.MenuItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${client.urls.menu-service}")
    private String menuServiceUrl;

    @SneakyThrows
    public MenuItemDto getMenuItemById(Long id) {
        log.info("ActionLog.MenuClient.getMenuItemById.start - id: {}", id);
        var url = String.format(menuServiceUrl + "/%d", id);

        try {
            var menuItem = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(MenuItemDto.class);
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
