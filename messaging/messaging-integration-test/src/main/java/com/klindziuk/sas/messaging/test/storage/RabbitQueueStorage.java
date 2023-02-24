package com.klindziuk.sas.messaging.test.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RabbitQueueStorage {

  private RabbitQueueStorage() {
    throw new RuntimeException();
  }

  private static final Map<String, String> STORAGE = new ConcurrentHashMap<>();

  private String addQueue(String key, String rabbitQueue) {
    return STORAGE.put(key, rabbitQueue);
  }

  private String removeQueue(String key) {
    return STORAGE.remove(key);
  }
}
