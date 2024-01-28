package com.gonion.kafkasample.listener;

import com.gonion.kafkasample.dto.MessageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
  @KafkaListener(
          topics = "gonion",
          groupId = "groupId",
          containerFactory = "messageFactory"
  )
  public void listen(MessageRequest data) {
    System.out.println("Listener received: " + data + " 🎉");
  }
}
