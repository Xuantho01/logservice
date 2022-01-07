package com.vpbank.logservice.rabbit.listener;

import com.vpbank.logservice.model.dto.LogElasticsearchModel;
import com.vpbank.logservice.model.dto.LogMessageDto;
import com.vpbank.logservice.model.mapper.LogElasticsearchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LogRabbitMQListener {
    private static final Logger logger = LoggerFactory.getLogger(LogRabbitMQListener.class);
    private final String INDEX_NAME = "index_log_message";

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    LogElasticsearchMapper logElasticsearchMapper;

    @RabbitListener(queues = "${rabbitmq.queue.log}")
    public void saveLogToElasticsearch(LogMessageDto logMessageDto) {
        try {
            logger.info("============================> message <=============================");
            logger.info("========================= received message " + logMessageDto.getMessage() + " ===============: " + logMessageDto.getStatus());
            IndexQuery indexQuery = new IndexQuery();
            LogElasticsearchModel model = logElasticsearchMapper.toElasticsearchModel(logMessageDto);
            indexQuery.setObject(model);

            List<IndexQuery> indexQueries = new ArrayList<>();
            indexQueries.add(indexQuery);
            elasticsearchOperations.bulkIndex(indexQueries, IndexCoordinates.of(INDEX_NAME));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("====================== save failed");
        }
    }
}
