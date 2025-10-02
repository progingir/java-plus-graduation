package ru.practicum.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties props;

    @Bean
    public ConsumerFactory<String, UserActionAvro> userActionsConsumerFactory() {
        final Map<String, Object> config = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers(),
                ConsumerConfig.GROUP_ID_CONFIG, props.getUserActionsConsumer().getGroupId(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, props.getUserActionsConsumer().getKeyDeserializer(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, props.getUserActionsConsumer().getValueDeserializer()
        );
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserActionAvro> userActionsKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserActionAvro> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userActionsConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, EventSimilarityAvro> eventSimilarityConsumerFactory() {
        Map<String, Object> config = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers(),
                ConsumerConfig.GROUP_ID_CONFIG, props.getEventsSimilarityConsumer().getGroupId(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, getClassFromString(props.getEventsSimilarityConsumer().getKeyDeserializer()),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getClassFromString(props.getEventsSimilarityConsumer().getValueDeserializer())
        );
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventSimilarityAvro> eventSimilarityKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EventSimilarityAvro> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(eventSimilarityConsumerFactory());
        return factory;
    }

    private Class<?> getClassFromString(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to load class: " + className, e);
        }
    }
}