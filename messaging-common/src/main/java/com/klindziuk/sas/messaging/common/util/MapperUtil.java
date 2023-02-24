package com.klindziuk.sas.messaging.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klindziuk.sas.messaging.common.EnvelopeMessage;

public final class MapperUtil {

  private MapperUtil() {
    throw new RuntimeException();
  }

  public static final ObjectMapper MAPPER = new ObjectMapper();

  public static EnvelopeMessage convertBody(byte[] body) {
    try {
      return MAPPER.readValue(body, EnvelopeMessage.class);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
