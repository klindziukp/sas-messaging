package com.klindziuk.sas.messaging.test.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMqConfig {

  @Value("${sas.messaging.rabbitmq.host}")
  private String host;

  @Value("${sas.messaging.rabbitmq.virtual-host}")
  private String virtualHost;

  @Value("${sas.messaging.rabbitmq.port}")
  private Integer port;

  @Value("${sas.messaging.rabbitmq.username}")
  private String username;

  @Value("${sas.messaging.rabbitmq.password}")
  private String password;

  @Value("${sas.messaging.rabbitmq.exchange.request}")
  private String requestExchangeName;

  @Value("${sas.messaging.rabbitmq.exchange.reply}")
  private String replyExchangeName;

  @Value("${sas.messaging.rabbitmq.exchange.heartbeat}")
  private String heartbeatExchangeName;

  @Value("${sas.messaging.rabbitmq.routing-key.request}")
  private String requestRoutingKey;

  @Value("${sas.messaging.rabbitmq.routing-key.reply}")
  private String replyRoutingKey;

  @Value("${sas.messaging.rabbitmq.routing-key.heartbeat}")
  private String heartBeatRoutingKey;
}
