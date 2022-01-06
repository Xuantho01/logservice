package com.vpbank.logservice.service.impl;

import com.vpbank.logservice.helper.JsonParserUtils;
import com.vpbank.logservice.model.entity.AuditLog;
import com.vpbank.logservice.repository.AuditLogRepository;
import com.vpbank.logservice.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LoggingServiceImpl implements LoggingService {
    private static final String REQUEST_ID = "request_id";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingServiceImpl.class);
    private final Map<String, List<AuditLog>> thirdPartyAuditLogs = new HashMap<>();
    private final Map<String, AuditLog> auditLogs = new HashMap<>();

    @Autowired
    AuditLogRepository auditLogRepository;

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        try {
            if (httpServletRequest.getRequestURI().contains("medias")) {
                return;
            }
            Object requestId = Thread.currentThread();
            String rqBody = JsonParserUtils.objectToString(body);
            StringBuilder data = new StringBuilder();
            data.append("\nLOGGING REQUEST BODY-----------------------------------\n")
                    .append("[REQUEST-ID]: ").append(requestId).append("\n")
                    .append("[BODY REQUEST]: ").append("\n")
                    .append(rqBody)
                    .append("\n")
                    .append("LOGGING REQUEST BODY-----------------------------------\n");
            LOGGER.info(data.toString());
            AuditLog auditLog = getAuditLog();
            if (auditLog != null) {
                auditLog.setRequestBody(rqBody);
            }
        } catch (Exception e) {
            LOGGER.error("Get logRequest error.", e);
        }
    }

    public AuditLog getAuditLog() {
        AuditLog auditLog = auditLogs.get(Thread.currentThread().getName());
        if (auditLog == null) {
            auditLog = new AuditLog();
            auditLog.setRequestId(Thread.currentThread().getName());
            auditLog.setMessage("");
            auditLogs.put(Thread.currentThread().getName(), auditLog);
        }
        return auditLog;
    }


    @Override
    public void saveAuditLog() {
        try {
            if (auditLogs.containsKey(Thread.currentThread().getName())) {
                AuditLog auditLog = getAuditLog();
                auditLog.setEnd(LocalDateTime.now());
                auditLogRepository.save(auditLog);
            }
        } catch (Exception e) {
            LOGGER.error("SaveAuditLog error.", e);
            LOGGER.info("Audit log error {}: ", JsonParserUtils.objectToString(getAuditLog()));
        } finally {
            auditLogs.remove(Thread.currentThread().getName());
        }

        List<AuditLog> thirdPartyLogs = thirdPartyAuditLogs.get(Thread.currentThread().getName());
        try {
            if (!CollectionUtils.isEmpty(thirdPartyLogs)) {
                auditLogRepository.saveAll(thirdPartyLogs);
            }
        } catch (Exception e) {
            LOGGER.error("Save 3rd AuditLog error.", e);
            for (AuditLog thirdPartyLog : thirdPartyLogs) {
                LOGGER.info("3rd Audit log error: {}", JsonParserUtils.objectToString(thirdPartyLog));
            }
        } finally {
            thirdPartyAuditLogs.remove(Thread.currentThread().getName());
        }
    }

    @Override
    public void addThirdPartyAuditLogs(AuditLog auditLog) {
        auditLog.setRequestId(Thread.currentThread().getName());
        List<AuditLog> auditLogs = thirdPartyAuditLogs.computeIfAbsent(Thread.currentThread().getName(), k -> new ArrayList<>());
        auditLogs.add(auditLog);
    }

    @Override
    public void updateThreadName(String prefix) {
        String requestId = UUID.randomUUID().toString();
        requestId = prefix + "_" + requestId;
        Thread.currentThread().setName(requestId);
    }

    @Override
    public List<AuditLog> getAuditLogByWiName(String wiName) {
        return auditLogRepository.getListAuditLogByWiName(wiName);
    }


    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        try {
            if (httpServletRequest.getRequestURI().contains("medias")) {
                return;
            }
            StringBuilder data = new StringBuilder();
            String rpBody = JsonParserUtils.objectToString(body);
            data.append("\nLOGGING RESPONSE-----------------------------------\n")
                    .append("[REQUEST-ID]: ").append(Thread.currentThread().getName()).append("\n")
                    .append("[BODY RESPONSE]: ").append("\n")
                    .append(rpBody)
                    .append("\n")
                    .append("LOGGING RESPONSE-----------------------------------\n");

            LOGGER.info(data.toString());
            AuditLog auditLog = getAuditLog();
            auditLog.setResponseBody(rpBody);
            auditLog.setResponseStatus(String.valueOf(httpServletResponse.getStatus()));
        } catch (Exception e) {
            LOGGER.error("Get logResponse error.", e);
        }

    }

    public AuditLog getAuditLog(String from, String to, String url, String responseStatus, String requestBody,
                                String responseBody, LocalDateTime start, LocalDateTime end, String params, String message, String wiName) {
        return new AuditLog(from, to, url, responseStatus, requestBody, responseBody, start, end, params, message, null, wiName);
    }

}



