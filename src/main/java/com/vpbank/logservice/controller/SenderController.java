package com.vpbank.logservice.controller;

import com.vpbank.logservice.model.dto.LogMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SenderController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Binding binding;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @GetMapping("/send/{msg}")
    public String send(@PathVariable("msg") String message){
        try {
            LogMessageDto logMessageDto = new LogMessageDto();
            logMessageDto.setMessage("welcome");
            logMessageDto.setStatus("success");
            rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), logMessageDto);
        } catch (AmqpException e) {
            e.printStackTrace();
            return "failed";
        }
        return "sent";
    }
}
