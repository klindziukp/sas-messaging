package com.klindziuk.sas.messaging.test.storage;

import com.klindziuk.sas.messaging.test.config.RabbitMqConfig;
import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.service.rabbitmq.MessagingConsumer;
import com.klindziuk.sas.messaging.test.util.RandomUtil;
import com.rabbitmq.client.Channel;

public final class RabbitQueueModelStorage {

  private RabbitQueueModelStorage() {
    throw new RuntimeException();
  }

  public static RabbitQueueModel heartbeat(
      RabbitMqConfig rabbitMqConfig, Channel channel, MessagingConsumer messagingConsumer) {
    return new RabbitQueueModel()
        .setQueueName(RandomUtil.randomQueueName())
        .setChannel(channel)
        .setConsumer(messagingConsumer)
        .setMessageConsumer(messagingConsumer)
        .setRoutingKey(rabbitMqConfig.getHeartBeatRoutingKey())
        .setExchange(rabbitMqConfig.getHeartbeatExchangeName());
  }

  public static RabbitQueueModel request(
      RabbitMqConfig rabbitMqConfig, Channel channel, MessagingConsumer messagingConsumer) {
    return new RabbitQueueModel()
        .setQueueName(RandomUtil.randomQueueName())
        .setChannel(channel)
        .setConsumer(messagingConsumer)
        .setMessageConsumer(messagingConsumer)
        .setRoutingKey(rabbitMqConfig.getRequestRoutingKey())
        .setExchange(rabbitMqConfig.getRequestExchangeName());
  }

  public static RabbitQueueModel reply(
      RabbitMqConfig rabbitMqConfig, Channel channel, MessagingConsumer messagingConsumer) {
    return new RabbitQueueModel()
        .setQueueName(RandomUtil.randomQueueName())
        .setChannel(channel)
        .setConsumer(messagingConsumer)
        .setMessageConsumer(messagingConsumer)
        .setExchange(rabbitMqConfig.getReplyExchangeName())
        .setRoutingKey(rabbitMqConfig.getReplyRoutingKey());
  }
}
