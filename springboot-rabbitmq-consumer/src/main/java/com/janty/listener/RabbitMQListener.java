package com.janty.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 15:07
 * @description:
 */
@Component
public class RabbitMQListener {
    @RabbitListener(queues = "boot_queue")
    public void listenerQueue(Message message){
        System.out.println("new String(message.getBody())" + new String(message.getBody()));
    }
}
