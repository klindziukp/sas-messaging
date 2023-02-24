package com.klindziuk.sas.producer.service;

import com.klindziuk.sas.messaging.common.EnvelopeMessage;
import com.klindziuk.sas.producer.config.RabbitMqConfig;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

  private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);

  private final AmqpTemplate amqpTemplate;
  private final RabbitMqConfig rabbitMqConfig;

  @Autowired
  public RabbitMQSender(AmqpTemplate amqpTemplate, RabbitMqConfig rabbitMqConfig) {
    this.amqpTemplate = amqpTemplate;
    this.rabbitMqConfig = rabbitMqConfig;
  }

  public void sendRequestMessage(Object message) {
    log.info("Sending request message: {}", message);
    amqpTemplate.convertAndSend(
        rabbitMqConfig.getRequestExchangeName(), rabbitMqConfig.getRequestRoutingKey(), message);
  }

  public void sendHeartbeatMessage(Object message) {
    log.info("Sending heartbeat message: {}", message);
    amqpTemplate.convertAndSend(
        rabbitMqConfig.getHeartbeatExchangeName(),
        rabbitMqConfig.getHeartBeatRoutingKey(),
        message);
  }

  public void sendHeartbeatMessages(int duration) {
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    Runnable messageRunnable =
        () -> sendHeartbeatMessage(new EnvelopeMessage(UUID.randomUUID(), "My Heart is beating!"));
    ScheduledFuture<?> runnableHandler =
        scheduler.scheduleAtFixedRate(messageRunnable, 0, 3, TimeUnit.SECONDS);
    Runnable canceller =
        () -> {
          runnableHandler.cancel(false);
          scheduler.shutdown(); // <---- Now the call is within the `canceller` Runnable.
        };
    scheduler.schedule(canceller, duration, TimeUnit.SECONDS);
  }
}
