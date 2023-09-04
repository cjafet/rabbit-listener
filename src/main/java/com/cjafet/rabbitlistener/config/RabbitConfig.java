package com.cjafet.rabbitlistener.config;

import com.cjafet.rabbitlistener.listener.RabbitMQMessageListener;
import org.springframework.amqp.core.*;
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

    public static final String QUEUE_NAME = "AppQueue";
    public static final String TOPIC_EXCHANGE = "TopicExchange";
    public static final String ROUTING_KEY = "topic";


    /**
     * Creates a RabbitMQ broker connection using ConnectionFactory
     *
     *
     * @return      the connection with RabbitMQ
     * @see         ConnectionFactory
     */
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost", 5672);
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
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * Creates an Exchange in RabbitMQ
     *
     *
     * @return      the exchange to be created in RabbitMQ
     * @see         Exchange
     */
    @Bean
    Exchange exchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * Bind an Exchange to a Queue
     *
     *
     * @return      the binding between an exchange and a queue
     * @see         Exchange
     */
    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY)
                .noargs();

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
