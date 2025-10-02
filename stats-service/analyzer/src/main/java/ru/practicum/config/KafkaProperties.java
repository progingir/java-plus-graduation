package ru.practicum.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private final ConsumerProps userActionsConsumer = new ConsumerProps();
    private final ConsumerProps eventsSimilarityConsumer = new ConsumerProps();

    @Data
    public static class ConsumerProps {
        private String topic;
        private String groupId;
        private String keyDeserializer;
        private String valueDeserializer;
    }
}