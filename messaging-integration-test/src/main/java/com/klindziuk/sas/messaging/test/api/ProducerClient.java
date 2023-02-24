package com.klindziuk.sas.messaging.test.api;

import com.klindziuk.sas.messaging.test.model.response.RequestReplyAuditResponse;
import com.klindziuk.sas.messaging.test.model.response.RequestReplyResponse;
import feign.Param;
import feign.RequestLine;

public interface ProducerClient {

  @RequestLine("GET /producer/request-reply/{phrase}")
  RequestReplyResponse requestReply(@Param("phrase") String phrase);

  @RequestLine("GET /producer/audit/request-reply")
  RequestReplyAuditResponse requestReplyAudit();
}
