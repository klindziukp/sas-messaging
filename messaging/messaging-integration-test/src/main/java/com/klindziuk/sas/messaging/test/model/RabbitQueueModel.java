package com.klindziuk.sas.messaging.test.model;

import com.klindziuk.sas.messaging.test.service.rabbitmq.MessageConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RabbitQueueModel {

  private Channel channel;
  private String queueName;
  private String exchange;
  private String routingKey;
  private Consumer consumer;
  private MessageConsumer messageConsumer;
}
