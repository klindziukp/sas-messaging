package com.klindziuk.sas.messaging.test.api;

import com.klindziuk.sas.messaging.test.BaseMessagingTest;
import com.klindziuk.sas.messaging.test.model.EnvelopeMessage;
import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.model.response.HeartbeatResponse;
import com.klindziuk.sas.messaging.test.service.rabbitmq.MessagingConsumer;
import com.klindziuk.sas.messaging.test.storage.RabbitQueueModelStorage;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeartbeatConsumerApiTest extends BaseMessagingTest {

  @Test
  public void testHeartbeatMessagingViaApi() {
    final Channel channel = rabbitMqChannel;
    final MessagingConsumer messagingConsumer = new MessagingConsumer(channel);
    final RabbitQueueModel rabbitQueueModel =
        RabbitQueueModelStorage.heartbeat(rabbitMqConfig, channel, messagingConsumer);
    rabbitQueueService.createAndSubscribeQueue(rabbitQueueModel);

    final HeartbeatResponse heartbeatResponse = new HeartbeatService().healthClient().heartbeat(10);
    final HeartbeatResponse expectedHeartbeatResponse =
        new HeartbeatResponse().setStatus("Heartbeat message has been sent successfully.");

    Assertions.assertEquals(expectedHeartbeatResponse, heartbeatResponse);
    awaitService.awaitUntil(
        () ->
            rabbitQueueService.getReceivedMessages(rabbitQueueModel, EnvelopeMessage.class).size()
                == 4);
  }
}
