package com.vpbank.logservice.model.dto;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

//import javax.persistence.Id;

@Data
@Document(indexName = "index_sys_logs")
@Setting(settingPath = "/settings/settings.json")
public class LogElasticsearchModel {
    private String message;
    private String status;
}
