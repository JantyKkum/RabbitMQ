package com.janty.spring.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Janty
 * @projectName: RabbitMQ
 * @date: 2023/2/22 9:53
 * @description:
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ConsumerTest {

    @Test
    public void testConsume(){
        boolean flag = true;
        while (flag){

        }
    }
}
