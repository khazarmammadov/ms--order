package org.khazar.msOrder.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaHelper {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    public <T> void send(String topic, T message) {
        log.info("Sending to topic {}: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }


}
