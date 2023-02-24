package com.klindziuk.sas.messaging.test.service.await;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;

public class AwaitService {

  public void awaitUntil(Callable<Boolean> condition) {
    Awaitility.await()
        .atMost(20, TimeUnit.SECONDS)
        .pollInterval(Duration.ofSeconds(1))
        .until(condition);
  }
}
