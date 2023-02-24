package com.klindziuk.sas.messaging.test.util;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomUtil {

  private RandomUtil() {}

  public static String randomQueueName() {
    return "test-queue-"
        + RandomStringUtils.randomAlphabetic(5).toLowerCase()
        + "-name-"
        + RandomStringUtils.randomAlphabetic(5).toLowerCase();
  }
}
