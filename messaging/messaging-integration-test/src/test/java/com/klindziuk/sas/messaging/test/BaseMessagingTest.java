package com.klindziuk.sas.messaging.test;

import com.klindziuk.sas.messaging.test.api.ProducerService;
import com.klindziuk.sas.messaging.test.config.RabbitMqConfig;
import com.klindziuk.sas.messaging.test.context.RabbitMqContext;
import com.klindziuk.sas.messaging.test.context.TestContext;
import com.klindziuk.sas.messaging.test.model.EnvelopeMessage;
import com.klindziuk.sas.messaging.test.model.RabbitQueueModel;
import com.klindziuk.sas.messaging.test.model.response.RequestReplyAudit;
import com.klindziuk.sas.messaging.test.model.response.RequestReplyAuditResponse;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RabbitMqConfig.class, RabbitMqContext.class, TestContext.class})
public abstract class BaseMessagingTest extends BaseTest {

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

  protected boolean isAuditResponseContainsPhrase(String phrase) {
    final RequestReplyAuditResponse requestReplyAuditResponse =
        new ProducerService().producerAuditClient().requestReplyAudit();
    for (RequestReplyAudit requestReplyAudit : requestReplyAuditResponse.getAudits()) {
      if (phrase.toUpperCase().equals(requestReplyAudit.getMessage())) {
        return true;
      }
    }
    return false;
  }
}
