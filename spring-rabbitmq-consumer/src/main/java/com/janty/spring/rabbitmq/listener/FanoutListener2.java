package com.janty.spring.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 10:30
 * @description:
 */
public class FanoutListener2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
        System.out.println("message.getMessageProperties().getConsumerTag() = " + message.getMessageProperties().getConsumerTag());
        System.out.println("message.getMessageProperties().getDeliveryTag() = " + message.getMessageProperties().getDeliveryTag());
        System.out.println("message.getMessageProperties().getReceivedExchange() = " + message.getMessageProperties().getReceivedExchange());
        System.out.println("message.getMessageProperties().getReceivedRoutingKey() = " + message.getMessageProperties().getReceivedRoutingKey());
    }
}
