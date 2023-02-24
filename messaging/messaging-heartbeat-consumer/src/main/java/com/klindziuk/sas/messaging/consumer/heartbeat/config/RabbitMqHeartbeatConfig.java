package com.klindziuk.sas.messaging.consumer.heartbeat.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMqHeartbeatConfig {

  @Value("${spring.rabbitmq.host}")
  private String host;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Value("${sas.messaging.rabbitmq.queue.heartbeat}")
  private String heartBeatQueueName;

  @Value("${sas.messaging.rabbitmq.exchange.heartbeat}")
  private String heartbeatExchangeName;

  @Value("${sas.messaging.rabbitmq.routing-key.heartbeat}")
  private String heartBeatRoutingKey;
}
