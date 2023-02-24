package com.klindziuk.sas.messaging.test.messaging;

import com.klindziuk.sas.messaging.test.BaseTest;
import com.klindziuk.sas.messaging.test.model.EnvelopeMessage;
import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.service.rabbitmq.MessagingConsumer;
import com.klindziuk.sas.messaging.test.storage.RabbitQueueModelStorage;
import com.klindziuk.sas.messaging.test.util.MapperUtil;
import com.rabbitmq.client.Channel;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class RequestReplyConsumerMessagingTest extends BaseTest {

  @Test
  public void testRequestReplyMessagingViaPublisher() {
    final Channel channel = rabbitMqChannel;
    final MessagingConsumer messagingConsumer = new MessagingConsumer(channel);
    final RabbitQueueModel rabbitQueueModel =
        RabbitQueueModelStorage.reply(rabbitMqConfig, channel, messagingConsumer);

    rabbitQueueService.createAndSubscribeQueue(rabbitQueueModel);

    final RabbitQueueModel requestQueueModel =
        RabbitQueueModelStorage.request(rabbitMqConfig, channel, messagingConsumer);
    final String phrase = "messaging-test-2-call-1";
    final EnvelopeMessage envelopeMessage =
        new EnvelopeMessage().setUuid(UUID.randomUUID()).setMessage(phrase);
    final String eventMessage = MapperUtil.fromObjectToJsonString(envelopeMessage);

    rabbitQueueService.publishToExchange(requestQueueModel, Collections.emptyMap(), eventMessage);
    awaitService.awaitUntil(() -> isMessageContainsPhrase(phrase, rabbitQueueModel));
  }
}
