package com.klindziuk.sas.messaging.test.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.util.List;
import java.util.Map;

public final class MapperUtil {

  private MapperUtil() {
    throw new RuntimeException();
  }

  public static final ObjectMapper MAPPER = new ObjectMapper();

  public static Map<String, Object> convertBody(byte[] bytes) {
    try {
      return MAPPER.readValue(bytes, new TypeReference<>() {});
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public static String fromObjectToJsonString(Object message) {
    try {
      return MAPPER.writeValueAsString(message);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public static <T> List<T> convertMessages(List<Map<String, Object>> list, Class<T> classOfT) {
    try {
      String jsonString = MAPPER.writeValueAsString(list);
      CollectionType collectionType =
          MAPPER.getTypeFactory().constructCollectionType(List.class, classOfT);
      return MAPPER.readValue(jsonString, collectionType);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
