package com.vpbank.logservice.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.log}")
    String queueLog;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.exchange}")
    String exchangeQueue;

    @Bean
    Queue logQueue(){
        return new Queue(queueLog, false);
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(exchangeQueue);
    }

    @Bean
    Binding binding(Queue log, DirectExchange exchange){
        return BindingBuilder.bind(log).to(exchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
}
