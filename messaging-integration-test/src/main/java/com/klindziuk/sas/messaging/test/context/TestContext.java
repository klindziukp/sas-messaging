package com.klindziuk.sas.messaging.test.context;

import com.klindziuk.sas.messaging.test.service.await.AwaitService;
import com.klindziuk.sas.messaging.test.service.rabbitmq.RabbitQueueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContext {

  @Bean
  public AwaitService awaitService() {
    return new AwaitService();
  }

  @Bean
  public RabbitQueueService rabbitQueueService() {
    return new RabbitQueueService();
  }
}
