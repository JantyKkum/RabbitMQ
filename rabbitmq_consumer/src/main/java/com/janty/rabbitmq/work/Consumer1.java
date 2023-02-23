package com.janty.rabbitmq.work;

import com.janty.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 9:01
 * @description:
 */
public class Consumer1 {
    public static final String QUEUE_NAME = "worker_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
//        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag = " + consumerTag);
                System.out.println("envelope.getExchange() = " + envelope.getExchange());
                System.out.println("envelope.getRoutingKey() = " + envelope.getRoutingKey());
                System.out.println("envelope.getDeliveryTag() = " + envelope.getDeliveryTag());
                System.out.println("properties = " + properties);
                System.out.println("new String(body) = " + new String(body));

            }
        };
        channel.basicConsume("worker_queue",true,defaultConsumer);
    }
}
