package com.janty.rabbitmq.topic;

import com.janty.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 9:29
 * @description:
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_topic";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);
        String queue1Name = "test_topic_queue1";
        String queue2Name = "test_topic_queue2";
        channel.queueDeclare(queue1Name,true,false,false,null);
        channel.queueDeclare(queue2Name,true,false,false,null);
        // 绑定队列和交换机
        /**
         *  参数：
         1. queue：队列名称
         2. exchange：交换机名称
         3. routingKey：路由键,绑定规则
         如果交换机的类型为fanout ,routingKey设置为""
         */
        // routing key  系统的名称.日志的级别。
        //需求： 所有error级别的日志存入数据库,所有order系统的日志存入数据库
        channel.queueBind(queue1Name,exchangeName,"#.error");
        channel.queueBind(queue1Name,exchangeName,"order.*");
        channel.queueBind(queue2Name,exchangeName,"#.haha");
        String body = "日志信息：张三调用了findAll方法...日志级别：info...";
        //发送消息goods.info,goods.error
        channel.basicPublish(exchangeName,"error.haha",null,body.getBytes());
        channel.close();
        connection.close();
    }
}
