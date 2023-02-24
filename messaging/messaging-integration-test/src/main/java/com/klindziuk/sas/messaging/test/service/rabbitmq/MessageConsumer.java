package com.klindziuk.sas.messaging.test.service.rabbitmq;

import java.util.List;
import java.util.Map;

public interface MessageConsumer {

  List<Map<String, Object>> getReceivedMessages();
}
