package com.janty.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 8:32
 * @description:
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.6.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare("simple_queue", true, false, false, null);     //如果没有一个名字叫simple_queue的队列,则会创建该队列,如果有则不会创建
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
        channel.basicConsume("simple_queue",true,defaultConsumer);


    }
}
