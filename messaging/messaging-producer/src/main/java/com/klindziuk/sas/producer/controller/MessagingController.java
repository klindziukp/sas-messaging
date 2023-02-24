package com.klindziuk.sas.producer.controller;

import com.klindziuk.sas.messaging.common.EnvelopeMessage;
import com.klindziuk.sas.producer.model.response.HeartbeatResponse;
import com.klindziuk.sas.producer.model.response.RequestReplyResponse;
import com.klindziuk.sas.producer.service.RabbitMQSender;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

  private final RabbitMQSender rabbitMQSender;

  @Autowired
  public RabbitMqController(RabbitMQSender rabbitMQSender) {
    this.rabbitMQSender = rabbitMQSender;
  }

  @GetMapping(value = "/producer/heartbeat/{duration}")
  public HeartbeatResponse heartbeat(@PathVariable int duration) {
    rabbitMQSender.sendHeartbeatMessages(duration);
    return new HeartbeatResponse("Heartbeat message has been sent successfully.");
  }

  @GetMapping(value = "/producer/request-reply/{phrase}")
  public RequestReplyResponse requestReply(@PathVariable String phrase) {
    rabbitMQSender.sendRequestMessage(new EnvelopeMessage(UUID.randomUUID(), phrase));
    return new RequestReplyResponse("Request-Reply message has been sent successfully.");
  }
}
