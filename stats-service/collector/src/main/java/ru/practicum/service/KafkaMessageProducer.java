package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import ru.practicum.config.KafkaProperties;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducer implements MessageProducer {

    private final KafkaTemplate<String, UserActionAvro> kafkaTemplate;
    private final KafkaProperties properties;

    @Override
    public void sendUserAction(UserActionAvro userActionAvro) {
        kafkaTemplate.send(properties.getUserActionsTopic(), userActionAvro);
    }
}