package com.janty.rabbitmq.work;

import com.janty.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 8:54
 * @description:
 */
public class Producer {
    public static final String QUEUE_NAME = "worker_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        for (int i = 0; i < 10; i++) {
            String message = "消息"+ (i+1);
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        }
        ConnectionUtil.closeResource(channel,connection);
    }
}
