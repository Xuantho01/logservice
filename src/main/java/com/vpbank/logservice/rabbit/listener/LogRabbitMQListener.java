package com.vpbank.logservice.rabbit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Component
public class LogRabbitMQListener {
    private static final Logger logger = LoggerFactory.getLogger(LogRabbitMQListener.class);

    @RabbitListener(queues = "${rabbitmq.queue.mail}")
    public void saveLogToElasticsearch() {
    }
}
