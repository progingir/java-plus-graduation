package ru.practicum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Configuration
public class KafkaConsumerConfig {

    private final ConsumerFactory<String, UserActionAvro> consumerFactory;

    public KafkaConsumerConfig(ConsumerFactory<String, UserActionAvro> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserActionAvro> userActionsKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserActionAvro> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}