package com.klindziuk.sas.producer.model.response;

import com.klindziuk.sas.producer.model.domain.RequestReplyAudit;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestReplyAuditResponse {

  private List<RequestReplyAudit> audits;
}
