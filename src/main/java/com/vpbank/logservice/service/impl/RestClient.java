//package com.vpbank.logservice.service.impl;
//
//
//import com.vpbank.logservice.exception.ServiceNotAvailableException;
//import com.vpbank.logservice.helper.Constant;
//import com.vpbank.logservice.helper.JsonParserUtils;
//import com.vpbank.logservice.model.entity.AuditLog;
//import com.vpbank.logservice.service.LoggingService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.retry.annotation.Retryable;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//
//@Service
//public class RestClient {
//
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Value("${ecm-gateway.host}")
//    private String ecmGatewayHost;
//
//    @Value("${ecm-gateway.key}")
//    private String ecmKey;
//
//
//    @Autowired
//    private LoggingService loggingService;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
//
//
//    @Retryable(value = {ServiceNotAvailableException.class})
//    public ResponseEntity callEcmService(HttpEntity<?> entity, String api, HttpMethod httpMethod, Class responseType, String requestData) throws ServiceNotAvailableException {
//        AuditLog auditLog = null;
//        LocalDateTime startTime = LocalDateTime.now();
//        api = ecmGatewayHost + api;
//        try {
//            LOGGER.info("Call ECM api: {}", api);
//            if (entity == null) {
//                HttpHeaders headers = new HttpHeaders();
//                headers.set(Constant.AUTHORIZATION, ecmKey);
//                entity = new HttpEntity(headers);
//            }
//            ResponseEntity response = restTemplate.exchange(api, httpMethod, entity, responseType);
//            auditLog = loggingService.getAuditLog(C           onstant.APP_NAME, "ECM Gateway", api, String.valueOf(response.getStatusCode().value()),
//                    requestData, response == null ? "" : JsonParserUtils.objectToString(response.getBody()),
//                    startTime, LocalDateTime.now(), null, null, null);
//            return response;
//        } catch (Exception e) {
//            LOGGER.error("callEcmService error", e);
//            auditLog = loggingService.getAuditLog(Constant.APP_NAME, "ECM Gateway", api, null,
//                    requestData, null,
//                    startTime, LocalDateTime.now(), null, e.getMessage(), null);
//            throw new ServiceNotAvailableException(e.getMessage(), e);
//        } finally {
//            loggingService.addThirdPartyAuditLogs(auditLog);
//        }
//
//    }
//
//}
