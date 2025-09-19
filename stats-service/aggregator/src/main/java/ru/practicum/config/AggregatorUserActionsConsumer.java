package ru.practicum.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.service.SimilarityService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregatorUserActionsConsumer {

    private final SimilarityService similarityService;

    @KafkaListener(
            topics = "${kafka.user-actions-consumer.topic}",
            containerFactory = "userActionsKafkaListenerFactory"
    )
    public void consumeUserAction(UserActionAvro message) {
        log.info("consumeUserAction Kafka: {}", message);
        similarityService.processUserAction(message);
    }
}