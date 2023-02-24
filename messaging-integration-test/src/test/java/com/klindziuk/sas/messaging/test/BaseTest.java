package com.klindziuk.sas.messaging.test;

import com.klindziuk.sas.messaging.test.config.RabbitMqConfig;
import com.klindziuk.sas.messaging.test.context.RabbitMqContext;
import com.klindziuk.sas.messaging.test.context.TestContext;
import com.klindziuk.sas.messaging.test.service.await.AwaitService;
import com.klindziuk.sas.messaging.test.service.rabbitmq.RabbitQueueService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
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
}
