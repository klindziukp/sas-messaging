package com.klindziuk.sas.messaging.test.api;

import com.klindziuk.sas.messaging.test.model.response.HeartbeatResponse;
import feign.Feign;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public class HeartbeatService {

  public HeartbeatService() {}

  public HeartbeatClient healthClient() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .logger(new Slf4jLogger(HeartbeatResponse.class))
        .logLevel(Level.FULL)
        .target(HeartbeatClient.class, "http://localhost:8080/");
  }
}
