package com.modulith.petrolstats.reports;

import com.modulith.petrolstats.stations.CacheUpdated;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CacheUpdatedListener {
    private final RabbitTemplate template;
    private final DirectExchange directExchange;

    public CacheUpdatedListener(RabbitTemplate template, DirectExchange directExchange) {
        this.template = template;
        this.directExchange = directExchange;
    }

    @EventListener
    void onCacheUpdated(CacheUpdated event) {
        System.out.println("Generating new reports ...");
        template.convertAndSend(directExchange.getName(), "prices.updated", "PricesUpdated");
    }
}
