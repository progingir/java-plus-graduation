package ru.practicum.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties("spring.kafka")
public class KafkaProperties {

    private String bootstrapServers;

    private Producer producer = new Producer();

    @Value("${collector.kafka.topic}")
    private String userActionsTopic;

    @Data
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
    }
}