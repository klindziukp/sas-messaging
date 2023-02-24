package com.klindziuk.sas.messaging.consumer.heartbeat.service;

import com.klindziuk.sas.messaging.common.EnvelopeMessage;
import com.klindziuk.sas.messaging.common.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqHeartBeatListener implements MessageListener {

  private static final Logger log = LoggerFactory.getLogger(RabbitMqHeartBeatListener.class);

  public void onMessage(Message message) {
    log.info("Consuming heartbeat message: {},", message);
    EnvelopeMessage envelopeMessage = MapperUtil.convertBody(message.getBody());
    log.info("Envelope message: {},", envelopeMessage);
  }
}
