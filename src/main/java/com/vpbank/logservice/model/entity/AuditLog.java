package com.vpbank.logservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "from_service")
    private String from;

    @Column(name = "to_service")
    private String to;

    @Column(name = "url")
    private String url;

    @Column(name = "response_status")
    private String responseStatus;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "response_body")
    private String responseBody;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    @Column(name = "params")
    private String params;

    @Column(name = "message")
    private String message;

    @Column(name = "source")
    private String source;

    @Nullable
    @Column(name = "wi_name")
    private String wiName;


    public AuditLog(String from, String to, String url, String responseStatus, String requestBody,
                    String responseBody, LocalDateTime start, LocalDateTime end, String params, String message, String source, String wiName) {
        this.from = from;
        this.to = to;
        this.url = url;
        this.responseStatus = responseStatus;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.start = start;
        this.end = end;
        this.params = params;
        this.message = message;
        this.source = source;
        this.wiName = wiName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWiName() {
        return wiName;
    }

    public void setWiName(String wiName) {
        this.wiName = wiName;
    }
}
