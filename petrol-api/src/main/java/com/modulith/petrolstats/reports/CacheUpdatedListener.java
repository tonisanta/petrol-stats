package com.modulith.petrolstats.reports;

import com.modulith.petrolstats.stations.CacheUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CacheUpdatedListener {
    private final Logger logger = LoggerFactory.getLogger(CacheUpdatedListener.class);
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    public CacheUpdatedListener(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @EventListener
    void onCacheUpdated(CacheUpdated event) {
        logger.info("Generating new reports ...");
        rabbitTemplate.convertAndSend(directExchange.getName(), "prices.updated", "");
    }
}
