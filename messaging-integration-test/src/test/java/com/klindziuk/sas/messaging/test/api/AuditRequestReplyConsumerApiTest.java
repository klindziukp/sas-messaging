package com.klindziuk.sas.messaging.test.api;

import com.klindziuk.sas.messaging.test.BaseMessagingTest;
import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.model.response.RequestReplyResponse;
import com.klindziuk.sas.messaging.test.service.rabbitmq.MessagingConsumer;
import com.klindziuk.sas.messaging.test.storage.RabbitQueueModelStorage;
import com.klindziuk.sas.messaging.test.util.RandomUtil;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuditRequestReplyConsumerApiTest extends BaseMessagingTest {

  @Test
  public void testRequestReplyAuditMessagingViaApi() {
    final Channel channel = rabbitMqChannel;
    final MessagingConsumer messagingConsumer = new MessagingConsumer(channel);
    final RabbitQueueModel rabbitQueueModel =
        RabbitQueueModelStorage.reply(rabbitMqConfig, channel, messagingConsumer);

    rabbitQueueService.createAndSubscribeQueue(rabbitQueueModel);

    final String phrase = RandomUtil.randomPhrase();
    final RequestReplyResponse requestReplyResponse =
        new ProducerService().producerClient().requestReply(phrase);

    final RequestReplyResponse expectedRequestReplyResponse =
        new RequestReplyResponse().setStatus("Request-Reply message has been sent successfully.");

    Assertions.assertEquals(expectedRequestReplyResponse, requestReplyResponse);
    awaitService.awaitUntil(() -> isMessageContainsPhrase(phrase, rabbitQueueModel));
    Assertions.assertTrue(isAuditResponseContainsPhrase(phrase));
  }
}
