package com.klindziuk.sas.producer.config;

import com.klindziuk.sas.producer.listener.RabbitMqReplyListener;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Import(RabbitMqConfig.class)
@Configuration
public class RabbitMqMessagingConfig {

  @Autowired private RabbitMqConfig rabbitMqConfig;

  @Bean
  Queue requestQueue() {
    return new Queue(rabbitMqConfig.getRequestQueueName(), false);
  }

  @Bean
  Queue replyQueue() {
    return new Queue(rabbitMqConfig.getReplyQueueName(), false);
  }

  @Bean
  Queue heartBeatQueue() {
    return new Queue(rabbitMqConfig.getHeartBeatQueueName(), false);
  }

  @Bean(name = "requestTopicExchange")
  TopicExchange requestTopicExchange() {
    return new TopicExchange(rabbitMqConfig.getRequestExchangeName());
  }

  @Bean(name = "replyTopicExchange")
  TopicExchange replyTopicExchange() {
    return new TopicExchange(rabbitMqConfig.getReplyExchangeName());
  }

  @Bean(name = "heartbeatTopicExchange")
  TopicExchange heartbeatTopicExchange() {
    return new TopicExchange(rabbitMqConfig.getHeartbeatExchangeName());
  }

  @Bean
  Binding requestBinding(Queue requestQueue, TopicExchange requestTopicExchange) {
    return BindingBuilder.bind(requestQueue)
        .to(requestTopicExchange)
        .with(rabbitMqConfig.getRequestRoutingKey());
  }

  @Bean
  Binding replyBinding(Queue replyQueue, TopicExchange replyTopicExchange) {
    return BindingBuilder.bind(replyQueue)
        .to(replyTopicExchange)
        .with(rabbitMqConfig.getReplyRoutingKey());
  }

  @Bean
  Binding heartBeatBinding(Queue heartBeatQueue, TopicExchange heartbeatTopicExchange) {
    return BindingBuilder.bind(heartBeatQueue)
        .to(heartbeatTopicExchange)
        .with(rabbitMqConfig.getHeartBeatRoutingKey());
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  ConnectionFactory connectionFactory() {
    CachingConnectionFactory cachingConnectionFactory =
        new CachingConnectionFactory(rabbitMqConfig.getHost());
    cachingConnectionFactory.setUsername(rabbitMqConfig.getUsername());
    cachingConnectionFactory.setUsername(rabbitMqConfig.getPassword());
    return cachingConnectionFactory;
  }

  @Bean
  public RetryOperationsInterceptor retryInterceptor() {
    return RetryInterceptorBuilder.stateless()
        .maxAttempts(3)
        .backOffOptions(2000, 2.0, 100000)
        .build();
  }

  @Bean
  MessageListenerContainer messageListenerContainer(RabbitMqReplyListener rabbitMqReplyListener) {
    SimpleMessageListenerContainer simpleMessageListenerContainer =
        new SimpleMessageListenerContainer();
    simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
    simpleMessageListenerContainer.setQueues(replyQueue());
    simpleMessageListenerContainer.setMessageListener(rabbitMqReplyListener);
    simpleMessageListenerContainer.setAdviceChain(retryInterceptor());
    return simpleMessageListenerContainer;
  }
}
