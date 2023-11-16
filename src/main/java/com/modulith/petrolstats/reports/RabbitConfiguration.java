package com.modulith.petrolstats.reports;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("stations.events");
    }

    /**
     * We're interested only in currently flowing messages, not in the old ones. So we need to:
     * Firstly, whenever we connect to Rabbit, we need a fresh, empty queue
     * Secondly, once we disconnect the consumer, the queue should be automatically deleted.
     *
     * @return
     */
    @Bean
    public Queue autoDeleteQueue() {
        // AnonymousQueue, which creates a non-durable, exclusive, auto-delete queue with a generated name
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(directExchange).with("prices.updated");
    }
}
