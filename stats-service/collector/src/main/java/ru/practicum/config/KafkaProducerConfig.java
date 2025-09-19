package ru.practicum.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, UserActionAvro> userActionProducerFactory() {
        Map<String, Object> props = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, getClassFromString(kafkaProperties.getProducer().getKeySerializer()),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getClassFromString(kafkaProperties.getProducer().getValueSerializer()),
                "schema.registry.url", kafkaProperties.getSchemaRegistryUrl()
        );
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, UserActionAvro> userActionKafkaTemplate() {
        return new KafkaTemplate<>(userActionProducerFactory());
    }

    @Bean
    public ProducerFactory<String, EventSimilarityAvro> eventSimilarityProducerFactory() {
        Map<String, Object> props = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, getClassFromString(kafkaProperties.getProducer().getKeySerializer()),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getClassFromString(kafkaProperties.getProducer().getValueSerializer()),
                "schema.registry.url", kafkaProperties.getSchemaRegistryUrl()
        );
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, EventSimilarityAvro> eventSimilarityKafkaTemplate() {
        return new KafkaTemplate<>(eventSimilarityProducerFactory());
    }

    private Class<?> getClassFromString(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to load class: " + className, e);
        }
    }
}