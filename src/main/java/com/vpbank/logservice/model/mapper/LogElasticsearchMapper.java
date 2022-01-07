package com.vpbank.logservice.model.mapper;

import com.vpbank.logservice.model.dto.LogElasticsearchModel;
import com.vpbank.logservice.model.dto.LogMessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogElasticsearchMapper{
    LogElasticsearchModel toElasticsearchModel(LogMessageDto s);
}
