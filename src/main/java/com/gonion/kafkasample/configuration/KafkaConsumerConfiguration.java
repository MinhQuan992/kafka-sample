package com.gonion.kafkasample.configuration;

import com.gonion.kafkasample.dto.MessageRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  public Map<String, Object> consumerConfiguration() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    return props;
  }

  @Bean
  public ConsumerFactory<String, MessageRequest> consumerFactory() {
    var jsonDeserializer = new JsonDeserializer<MessageRequest>();
    jsonDeserializer.addTrustedPackages("com.gonion.kafkasample.*");

    return new DefaultKafkaConsumerFactory<>(
            consumerConfiguration(),
            new StringDeserializer(),
            jsonDeserializer
    );
  }

  @Bean
  public KafkaListenerContainerFactory<
          ConcurrentMessageListenerContainer<String, MessageRequest>> messageFactory(
                  ConsumerFactory<String, MessageRequest> consumerFactory) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, MessageRequest>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }
}
