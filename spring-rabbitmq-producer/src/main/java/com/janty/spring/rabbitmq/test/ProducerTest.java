package com.janty.spring.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 9:47
 * @description:
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 只发队列消息
     * 默认交换机类型为 direct
     * 交换机的名称为空,路由键为队列的名称
     */
    @Test
    public void simpleQueueTest(){
        //路由键与队列同名
        rabbitTemplate.convertAndSend("spring_simple_queue", "只发队列spring_simple_queue的消息。");
    }

    /**
     * 发送广播
     * 交换机类型为 fanout
     * 绑定到该交换机的所有队列都能够收到消息
     */
    @Test
    public void fanoutTest(){
        /**
         * 参数1：交换机名称
         * 参数2：路由键名（广播设置为空）
         * 参数3：发送的消息内容
         */
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "发送到spring_fanout_exchange交换机的广播消息");
    }

    /**
     * 通配符
     * 交换机类型为 topic
     * 匹配路由键的通配符,*表示一个单词,#表示多个单词
     * 绑定到该交换机的匹配队列能够收到对应消息
     */
    @Test
    public void topicTest(){
        /**
         * 参数1：交换机名称
         * 参数2：路由键名
         * 参数3：发送的消息内容
         */
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj", "发送到spring_topic_exchange交换机atguigu.bj的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj.1", "发送到spring_topic_exchange交换机atguigu.bj.1的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj.2", "发送到spring_topic_exchange交换机atguigu.bj.2的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "guigu.cn", "发送到spring_topic_exchange交换机guigu.cn的消息");
    }


    /**
     * 测试确认模式
     */
    @Test
    public void topicConfirm(){

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String s) {
                if(ack){
                    System.out.println("投递成功！");
                }else {
                    System.out.println("投递失败！失败原因为："+s);
                }
            }
        });
        rabbitTemplate.convertAndSend("spring_direct_exchange", "confirm","confirm的测试消息");      //正确的交换机名称
//        rabbitTemplate.convertAndSend("spring_direct_exchange111", "confirm","confirm的测试消息");      //错误的交换机名称
    }


    /**
     * 测试退回模式
     */
    @Test
    public void topicReturn(){
        //设置强制退回
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
                System.out.println("s = " + s);
                System.out.println("s1 = " + s1);
                System.out.println("s2 = " + s2);

            }
        });
        rabbitTemplate.convertAndSend("spring_direct_exchange", "confirm","confirm的测试消息");      //正确的交换机名称
//        rabbitTemplate.convertAndSend("spring_direct_exchange", "confirm111","confirm的测试消息");      //错误的交换机名称
    }


    /**
     * 发送测试死信消息：
     *  1. 过期时间
     *  2. 长度限制
     *  3. 消息拒收
     */
    @Test
    public void testDlx(){
        //1. 测试过期时间，死信消息
//        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？");

//        //2. 测试长度限制后,消息死信
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？");
//        }

        //3. 测试消息拒收
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,被拒收，不会放回元队列");
    }

}
