package com.gonion.kafkasample.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
  @KafkaListener(topics = "gonion", groupId = "groupId")
  public void listen(String data) {
    System.out.println("Listener received: " + data + " 🎉");
  }
}
