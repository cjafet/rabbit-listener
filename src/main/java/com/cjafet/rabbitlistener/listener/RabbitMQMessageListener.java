package com.cjafet.rabbitlistener.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Class responsible for implements MessageListener interface,
 *
 *
 * @author Carlos Jafet Neto
 *
 */
public class RabbitMQMessageListener implements MessageListener {

    /**
     * Overrides interface onMessage method
     *
     * @see         MessageListener
     */
    @Override
    public void onMessage(Message message) {
        System.out.println("message = " + message.getBody().toString());
    }
}
