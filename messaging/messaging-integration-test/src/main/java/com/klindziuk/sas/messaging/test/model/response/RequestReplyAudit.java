package com.klindziuk.sas.messaging.test.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestReplyAudit {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("message")
  private String message;

  @JsonProperty("createdAt")
  private Instant createdAt;
}
