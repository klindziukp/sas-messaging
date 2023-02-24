package com.klindziuk.sas.messaging.consumer.reply.service;

import com.klindziuk.sas.messaging.consumer.reply.config.RabbitMqReplyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqReplySender {

  private static final Logger log = LoggerFactory.getLogger(RabbitMqReplySender.class);

  private final AmqpTemplate amqpTemplate;
  private final RabbitMqReplyConfig rabbitMqConfig;

  @Autowired
  public RabbitMqReplySender(AmqpTemplate amqpTemplate, RabbitMqReplyConfig rabbitMqConfig) {
    this.amqpTemplate = amqpTemplate;
    this.rabbitMqConfig = rabbitMqConfig;
  }

  public void send(Object message) {
    log.info(
        "Send 'Reply' message '{}' to exchange: {}",
        message,
        rabbitMqConfig.getRequestExchangeName());
    amqpTemplate.convertAndSend(
        rabbitMqConfig.getReplyExchangeName(), rabbitMqConfig.getReplyRoutingKey(), message);
  }
}
