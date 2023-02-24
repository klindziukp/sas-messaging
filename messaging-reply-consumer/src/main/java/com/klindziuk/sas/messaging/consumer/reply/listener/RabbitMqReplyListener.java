package com.klindziuk.sas.messaging.consumer.reply.listener;

import com.klindziuk.sas.messaging.common.EnvelopeMessage;
import com.klindziuk.sas.messaging.common.util.MapperUtil;
import com.klindziuk.sas.messaging.consumer.reply.service.RabbitMqReplySender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqReplyListener implements MessageListener {

  private static final Logger log = LoggerFactory.getLogger(RabbitMqReplyListener.class);

  @Autowired private RabbitMqReplySender rabbitMqReplySender;

  public void onMessage(Message message) {
    EnvelopeMessage envelopeMessage = MapperUtil.convertBody(message.getBody());
    log.info("Consuming Envelope message: {},", envelopeMessage);
    envelopeMessage.setMessage(envelopeMessage.getMessage().toUpperCase());
    rabbitMqReplySender.send(envelopeMessage);
  }
}
