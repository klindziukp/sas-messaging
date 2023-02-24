package com.klindziuk.sas.messaging.test.context;

import com.klindziuk.sas.messaging.test.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Import(RabbitMqConfig.class)
@Configuration
public class RabbitMqContext {

  @Autowired private RabbitMqConfig rabbitMqConfig;

  @Bean(name = "rabbitMqConnectionFactory")
  public ConnectionFactory rabbitMqConnectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(rabbitMqConfig.getHost());
    connectionFactory.setPort(rabbitMqConfig.getPort());
    connectionFactory.setUsername(rabbitMqConfig.getUsername());
    connectionFactory.setPassword(rabbitMqConfig.getPassword());
    connectionFactory.setVirtualHost(rabbitMqConfig.getVirtualHost());

    return connectionFactory;
  }

  @Bean(name = "rabbitMqConnection")
  public Connection rabbitMqConnection(ConnectionFactory rabbitMqConnectionFactory) {
    try {
      return rabbitMqConnectionFactory.newConnection();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Bean(name = "rabbitMqChannel")
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public Channel rabbitMqChannel(Connection rabbitMqConnection) {
    try {
      return rabbitMqConnection.createChannel();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
