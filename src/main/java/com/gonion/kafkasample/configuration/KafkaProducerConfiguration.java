package com.gonion.kafkasample.configuration;

import com.gonion.kafkasample.dto.MessageRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
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
public class KafkaProducerConfiguration {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  public Map<String, Object> producerConfiguration() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return props;
  }

  @Bean
  // V is the type of objects we send, it can be a custom type like Customer
  public ProducerFactory<String, MessageRequest> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfiguration());
  }

  @Bean
  // The same notes for V as above
  public KafkaTemplate<String, MessageRequest> kafkaTemplate(ProducerFactory<String, MessageRequest> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }
}
