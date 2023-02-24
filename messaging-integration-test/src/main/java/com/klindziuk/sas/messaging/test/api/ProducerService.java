package com.klindziuk.sas.messaging.test.api;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.klindziuk.sas.messaging.test.model.response.RequestReplyResponse;
import feign.Feign;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import java.util.List;

public class ProducerService {

  public ProducerService() {}

  public ProducerClient producerClient() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .logger(new Slf4jLogger(RequestReplyResponse.class))
        .logLevel(Level.FULL)
        .target(ProducerClient.class, "http://localhost:8080/");
  }

  // Update with appropriate models
  public ProducerClient producerAuditClient() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder(List.of(new JavaTimeModule())))
        .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
        .logger(new Slf4jLogger(RequestReplyResponse.class))
        .logLevel(Level.FULL)
        .target(ProducerClient.class, "http://localhost:8080/");
  }
}
