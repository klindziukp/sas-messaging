package com.klindziuk.sas.messaging.test.api;

import com.klindziuk.sas.messaging.test.model.response.HeartbeatResponse;
import feign.Param;
import feign.RequestLine;

public interface HeartbeatClient {

  @RequestLine("GET /producer/heartbeat/{duration}")
  HeartbeatResponse heartbeat(@Param("duration") int duration);
}
