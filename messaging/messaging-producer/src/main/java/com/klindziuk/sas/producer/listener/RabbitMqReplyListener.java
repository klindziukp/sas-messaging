package com.klindziuk.sas.producer.listener;

import com.klindziuk.sas.messaging.common.EnvelopeMessage;
import com.klindziuk.sas.messaging.common.util.MapperUtil;
import com.klindziuk.sas.producer.model.domain.RequestReplyAudit;
import com.klindziuk.sas.producer.repository.RequestReplyAuditRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqReplyListener implements MessageListener {

  private static final Logger log = LoggerFactory.getLogger(RabbitMqReplyListener.class);

  private final RequestReplyAuditRepository requestReplyAuditRepository;

  @Autowired
  public RabbitMqReplyListener(RequestReplyAuditRepository requestReplyAuditRepository) {
    this.requestReplyAuditRepository = requestReplyAuditRepository;
  }

  public void onMessage(Message message) {
    EnvelopeMessage envelopeMessage = MapperUtil.convertBody(message.getBody());
    log.info("Consuming Envelope Reply Message: {},", envelopeMessage);

    final RequestReplyAudit requestReplyAudit =
        new RequestReplyAudit()
            .setMessage(envelopeMessage.getMessage())
            .setCreatedAt(Instant.now());
    requestReplyAuditRepository.save(requestReplyAudit);
  }
}
