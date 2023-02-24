package com.klindziuk.sas.messaging.test.service.rabbitmq;

import com.klindziuk.sas.messaging.test.util.MapperUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessagingConsumer extends DefaultConsumer implements Consumer, MessageConsumer {

  private final List<Map<String, Object>> envelopeMessages;

  public MessagingConsumer(Channel channel) {
    super(channel);
    envelopeMessages = new CopyOnWriteArrayList<>();
  }

  @Override
  public void handleDelivery(
      String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {

    envelopeMessages.add(MapperUtil.convertBody(body));
  }

  @Override
  public List<Map<String, Object>> getReceivedMessages() {
    return envelopeMessages;
  }
}
