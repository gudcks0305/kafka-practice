package com.example.kafkapractice.email_worker.config.kafka;

import com.example.kafkapractice.chat.infra.config.kafka.KafkaChatConstants;
import com.example.kafkapractice.email_worker.domain.SendEmail;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SendMailKafkaProducerConfig {


    private String bootstrapServers = "localhost:9092";

    @Bean
    public ProducerFactory<String, SendEmail> sendMailProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }
    private Map<String, Object> producerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaChatConstants.KAFKA_BROKER);
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configurations;
    }
    @Bean
    public KafkaTemplate<String, SendEmail> sendMailKafkaTemplate() {
        return new KafkaTemplate<>(sendMailProducerFactory());
    }


}
