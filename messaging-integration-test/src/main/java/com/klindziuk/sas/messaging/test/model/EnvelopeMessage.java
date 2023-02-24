package com.klindziuk.sas.messaging.test.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnvelopeMessage implements Serializable {

  private UUID uuid;
  private String message;
}
