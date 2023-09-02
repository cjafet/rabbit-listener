package com.cjafet.rabbitlistener.config;

import com.cjafet.rabbitlistener.listener.RabbitMQMessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Class responsible for handling RabbitMQ connections,
 * queues definitions and listener class binding
 *
 * @author Carlos Jafet Neto
 *
 */
@Configuration
public class RabbitConfig {

    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";


    /**
     * Creates a RabbitMQ broker connection using ConnectionFactory
     *
     *
     * @return      the connection with RabbitMQ
     * @see         ConnectionFactory
     */
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost", 5562);
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }


    /**
     * Creates a Queue in RabbitMQ
     *
     *
     * @return      the queue to be created in RabbitMQ
     * @see         Queue
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    /**
     * Creates a MessageListenerContainer to bind the listener class (RabbitMQMessageListener), the ConnectionFactory
     * and the Queue
     *
     * @return      the connection with RabbitMQ
     * @see         ConnectionFactory
     */
    @Bean
    MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQMessageListener());
        return simpleMessageListenerContainer;
    }

}
