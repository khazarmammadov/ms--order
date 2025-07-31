package org.khazar.msOrder.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaHelper<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(String topic, T message) {
        log.info("ActionLog.topic.message.start - topic: {}, message: {}", topic, message);
        kafkaTemplate.send(topic, message);
        log.info("ActionLog.topic.message.end - topic: {}, message: {}", topic, message);
    }

}
