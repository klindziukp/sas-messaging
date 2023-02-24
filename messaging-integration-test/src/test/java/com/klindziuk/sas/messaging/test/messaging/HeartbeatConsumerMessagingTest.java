package com.klindziuk.sas.messaging.test.messaging;

import com.klindziuk.sas.messaging.test.BaseMessagingTest;
import com.klindziuk.sas.messaging.test.model.EnvelopeMessage;
import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.service.rabbitmq.MessagingConsumer;
import com.klindziuk.sas.messaging.test.storage.RabbitQueueModelStorage;
import com.klindziuk.sas.messaging.test.util.MapperUtil;
import com.rabbitmq.client.Channel;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class HeartbeatConsumerMessagingTest extends BaseMessagingTest {

  @Test
  public void testHeartbeatMessagingViaPublisher() {
    final Channel channel = rabbitMqChannel;
    final MessagingConsumer messagingConsumer = new MessagingConsumer(channel);
    final RabbitQueueModel rabbitQueueModel =
        RabbitQueueModelStorage.heartbeat(rabbitMqConfig, channel, messagingConsumer);
    rabbitQueueService.createAndSubscribeQueue(rabbitQueueModel);

    final int size = 4;
    for (int i = 0; i < size; i++) {
      final String message = "This is heartbeat message";
      final EnvelopeMessage envelopeMessage =
          new EnvelopeMessage().setUuid(UUID.randomUUID()).setMessage(message);
      final String eventMessage = MapperUtil.fromObjectToJsonString(envelopeMessage);
      rabbitQueueService.publishToExchange(rabbitQueueModel, Collections.emptyMap(), eventMessage);
    }

    awaitService.awaitUntil(
        () ->
            rabbitQueueService.getReceivedMessages(rabbitQueueModel, EnvelopeMessage.class).size()
                == size);
  }
}
