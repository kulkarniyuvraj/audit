package com.scb.audit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scb.audit.model.AuditLog;

public interface CustomerAuditRepo extends JpaRepository<AuditLog, Long> {

}
