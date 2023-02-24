package com.klindziuk.sas.messaging.test.model.response;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestReplyAuditResponse {

  private List<RequestReplyAudit> audits;
}
