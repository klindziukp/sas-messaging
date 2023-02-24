package com.klindziuk.sas.producer.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;

@Table(name = "request_reply_audit")
@Entity
@Data
@Accessors(chain = true)
public class RequestReplyAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  private Long id;

  @JsonProperty("message")
  private String message;

  @JsonProperty("createdAt")
  private Instant createdAt;
}
