package com.vpbank.logservice.service;


import com.vpbank.logservice.model.entity.AuditLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface LoggingService {
    void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);

    void logRequest(HttpServletRequest httpServletRequest, Object body);

    AuditLog getAuditLog();

    AuditLog getAuditLog(String from, String to, String url, String responseStatus, String requestBody,
                                String responseBody, LocalDateTime start, LocalDateTime end, String params, String message,String winame);

    void saveAuditLog();

    void addThirdPartyAuditLogs(AuditLog auditLog);

    void updateThreadName(String prefix);

    List<AuditLog> getAuditLogByWiName(String wiName);
}
