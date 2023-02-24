package com.klindziuk.sas.producer.controller;

import com.klindziuk.sas.producer.model.domain.RequestReplyAudit;
import com.klindziuk.sas.producer.model.response.RequestReplyAuditResponse;
import com.klindziuk.sas.producer.repository.RequestReplyAuditRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {

  private final RequestReplyAuditRepository requestReplyAuditRepository;

  @Autowired
  public AuditController(RequestReplyAuditRepository requestReplyAuditRepository) {
    this.requestReplyAuditRepository = requestReplyAuditRepository;
  }

  @GetMapping(value = "/producer/audit/request-reply")
  public RequestReplyAuditResponse requestReplyAudit() {
    List<RequestReplyAudit> result = new ArrayList<>();
    requestReplyAuditRepository.findAll().iterator().forEachRemaining(result::add);
    return new RequestReplyAuditResponse().setAudits(result);
  }
}
