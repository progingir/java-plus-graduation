package ru.practicum.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.service.UserActionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActionsConsumer {

    private final UserActionService userActionService;

    @KafkaListener(
            topics = "${kafka.user-actions-consumer.topic}",
            containerFactory = "userActionsKafkaListenerFactory"
    )
    public void consumeUserActions(UserActionAvro message) {
        log.info("user action: {}", message);
        userActionService.updateUserAction(message);
    }
}