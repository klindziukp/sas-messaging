package com.klindziuk.sas.messaging.consumer.reply.config;

import com.klindziuk.sas.messaging.consumer.reply.listener.RabbitMqReplyListener;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

@Import(RabbitMqReplyConfig.class)
@Configuration
public class RabbitMqReplyMessagingConfig {

  @Autowired private RabbitMqReplyConfig rabbitMqReplyConfig;

  @Bean
  Queue requestQueue() {
    return new Queue(rabbitMqReplyConfig.getRequestQueueName(), false);
  }

  @Bean
  Queue replyQueue() {
    return new Queue(rabbitMqReplyConfig.getReplyQueueName(), false);
  }

  @Bean(name = "replyTopicExchange")
  TopicExchange replyTopicExchange() {
    return new TopicExchange(rabbitMqReplyConfig.getReplyExchangeName());
  }

  @Bean
  Binding binding(Queue replyQueue, TopicExchange replyTopicExchange) {
    return BindingBuilder.bind(replyQueue)
        .to(replyTopicExchange)
        .with(rabbitMqReplyConfig.getReplyRoutingKey());
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
        new CachingConnectionFactory(rabbitMqReplyConfig.getHost());
    cachingConnectionFactory.setUsername(rabbitMqReplyConfig.getUsername());
    cachingConnectionFactory.setUsername(rabbitMqReplyConfig.getPassword());
    return cachingConnectionFactory;
  }

  @Bean
  MessageListenerContainer messageListenerContainer(RabbitMqReplyListener rabbitMqReplyListener) {
    SimpleMessageListenerContainer simpleMessageListenerContainer =
        new SimpleMessageListenerContainer();
    simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
    simpleMessageListenerContainer.setQueues(requestQueue());
    simpleMessageListenerContainer.setMessageListener(rabbitMqReplyListener);
    return simpleMessageListenerContainer;
  }
}
