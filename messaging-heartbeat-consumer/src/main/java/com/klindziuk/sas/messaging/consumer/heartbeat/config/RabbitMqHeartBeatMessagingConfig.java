package com.klindziuk.sas.messaging.consumer.heartbeat.config;

import com.klindziuk.sas.messaging.consumer.heartbeat.listener.RabbitMqHeartbeatListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMqHeartbeatConfig.class)
public class RabbitMqHeartBeatMessagingConfig {

  @Autowired private RabbitMqHeartbeatConfig rabbitMqHeartBeatConfig;

  @Bean
  Queue heartBeatQueue() {
    return new Queue(rabbitMqHeartBeatConfig.getHeartBeatQueueName(), false);
  }

  @Bean
  ConnectionFactory connectionFactory() {
    CachingConnectionFactory cachingConnectionFactory =
        new CachingConnectionFactory(rabbitMqHeartBeatConfig.getHost());
    cachingConnectionFactory.setUsername(rabbitMqHeartBeatConfig.getUsername());
    cachingConnectionFactory.setUsername(rabbitMqHeartBeatConfig.getPassword());
    return cachingConnectionFactory;
  }

  @Bean
  MessageListenerContainer messageListenerContainer() {
    SimpleMessageListenerContainer simpleMessageListenerContainer =
        new SimpleMessageListenerContainer();
    simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
    simpleMessageListenerContainer.setQueues(heartBeatQueue());
    simpleMessageListenerContainer.setMessageListener(new RabbitMqHeartbeatListener());
    return simpleMessageListenerContainer;
  }
}
