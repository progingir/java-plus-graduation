package ru.practicum.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private String schemaRegistryUrl;
    private ConsumerProps userActionsConsumer = new ConsumerProps();
    private ConsumerProps eventsSimilarityConsumer = new ConsumerProps();
    private ProducerProps producer = new ProducerProps();

    @Data
    public static class ConsumerProps {
        private String topic;
        private String groupId;
        private String keyDeserializer;
        private String valueDeserializer;
    }

    @Data
    public static class ProducerProps {
        private String topic;
        private String keySerializer;
        private String valueSerializer;
    }
}