package ru.practicum.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.service.EventSimilarityService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventsSimilarityConsumer {

    private final EventSimilarityService eventSimilarityService;

    @KafkaListener(
            topics = "${kafka.events-similarity-consumer.topic}",
            containerFactory = "eventSimilarityKafkaListenerFactory"
    )
    public void consumeEventSimilarity(EventSimilarityAvro msg) {
        log.info("Полученное сходство событий: {}", msg);
        eventSimilarityService.updateEventSimilarity(msg);
    }
}