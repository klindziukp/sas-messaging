package com.klindziuk.sas.messaging.test;

import com.klindziuk.sas.messaging.test.config.RabbitMqConfig;
import com.klindziuk.sas.messaging.test.context.RabbitMqContext;
import com.klindziuk.sas.messaging.test.context.TestContext;
import com.klindziuk.sas.messaging.test.model.EnvelopeMessage;
import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.service.await.AwaitService;
import com.klindziuk.sas.messaging.test.service.rabbitmq.RabbitQueueService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RabbitMqConfig.class, RabbitMqContext.class, TestContext.class})
public abstract class BaseTest {

  @Autowired protected RabbitMqConfig rabbitMqConfig;
  @Autowired protected Connection rabbitMqConnection;
  @Autowired protected Channel rabbitMqChannel;
  @Autowired protected AwaitService awaitService;
  @Autowired protected RabbitQueueService rabbitQueueService;

  protected boolean isMessageContainsPhrase(String phase, RabbitQueueModel rabbitQueueModel) {
    boolean result = false;
    List<EnvelopeMessage> receivedMessages =
        rabbitQueueService.getReceivedMessages(rabbitQueueModel, EnvelopeMessage.class);
    for (EnvelopeMessage receivedMessage : receivedMessages) {
      if (phase.toUpperCase().equals(receivedMessage.getMessage())) {
        result = true;
        break;
      }
    }
    return result;
  }
}
