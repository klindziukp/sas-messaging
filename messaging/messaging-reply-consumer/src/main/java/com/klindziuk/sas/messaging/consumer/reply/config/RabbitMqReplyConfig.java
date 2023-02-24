package com.klindziuk.sas.messaging.consumer.reply.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMqReplyConfig {

  @Value("${spring.rabbitmq.host}")
  private String host;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Value("${sas.messaging.rabbitmq.queue.request}")
  private String requestQueueName;

  @Value("${sas.messaging.rabbitmq.queue.reply}")
  private String replyQueueName;

  @Value("${sas.messaging.rabbitmq.exchange.request}")
  private String requestExchangeName;

  @Value("${sas.messaging.rabbitmq.exchange.reply}")
  private String replyExchangeName;

  @Value("${sas.messaging.rabbitmq.routing-key.request}")
  private String requestRoutingKey;

  @Value("${sas.messaging.rabbitmq.routing-key.reply}")
  private String replyRoutingKey;
}
