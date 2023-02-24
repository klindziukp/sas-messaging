package com.klindziuk.sas.messaging.test.service.rabbitmq;

import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.util.MapperUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitQueueService {

  private static final Logger log = LoggerFactory.getLogger(RabbitQueueService.class);

  public RabbitQueueService() {}

  public void createAndSubscribeQueue(RabbitQueueModel rabbitQueueModel) {
    try {
      final String queueName = rabbitQueueModel.getQueueName();
      final String exchangeName = rabbitQueueModel.getExchange();
      final Channel channel = rabbitQueueModel.getChannel();
      log.info("Creating queue: '{}'", queueName);
      channel.queueDeclare(queueName, false, true, true, null);
      log.info("Binding queue '{}' to exchange: '{}'", queueName, exchangeName);
      channel.queueBind(queueName, exchangeName, rabbitQueueModel.getRoutingKey());
      channel.basicConsume(queueName, true, rabbitQueueModel.getConsumer());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public void publishToExchange(
      RabbitQueueModel rabbitQueueModel, Map<String, Object> properties, String message) {
    AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
    builder.contentEncoding(StandardCharsets.UTF_8.name());
    builder.headers(properties);
    AMQP.BasicProperties messageProperties = builder.build();
    log.info(
        "Sending RabbitMQ message '{}' to exchange: '{}'; using routing key: '{}'",
        message,
        rabbitQueueModel.getExchange(),
        rabbitQueueModel.getRoutingKey());
    try {
      rabbitQueueModel
          .getChannel()
          .basicPublish(
              rabbitQueueModel.getExchange(),
              rabbitQueueModel.getRoutingKey(),
              messageProperties,
              message.getBytes(StandardCharsets.UTF_8));
    } catch (Exception ex) {
      log.error("Unable to send message to exchange {}", rabbitQueueModel.getExchange());
      throw new RuntimeException(ex);
    }
  }

  public void deleteQueue(RabbitQueueModel rabbitQueueModel) {
    try {
      final String queueName = rabbitQueueModel.getQueueName();
      log.info("Deleting queue: '{}'", queueName);
      rabbitQueueModel.getChannel().queueDelete(queueName);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public <T> List<T> getReceivedMessages(RabbitQueueModel rabbitQueueModel, Class<T> classOfT) {
    final List<T> receivedMessages =
        MapperUtil.convertMessages(
            rabbitQueueModel.getMessageConsumer().getReceivedMessages(), classOfT);
    log.info("Messages received: '{}'", receivedMessages);
    return receivedMessages;
  }
}
