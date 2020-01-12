package pers.mangseng.activemq.consumer.prefetch_size;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : masteryourself
 * @version : 1.0
 * blog : https://blog.csdn.net/masteryourself
 * Tel : 17621208646
 * Description : prefetchSize 演示
 * @date : 2020/1/12 11:56
 */
public class QueueConsumer1 {

    public static void main(String[] args) throws Exception {
        //创建一个 JMS connection factory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.89.210:61616");
        //通过 connection factory 来创建 JMS connection
        Connection connection = connectionFactory.createConnection();
        //启动 JMS connection
        connection.start();
        //通过 connection 创建 JMS session，第一个参数表示使用事务
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        //创建 JMS destination
        Destination destination = session.createQueue("my-queue");
        //创建 JMS message，并设置 destination
        MessageConsumer consumer = session.createConsumer(destination);
        //注册一个 JMS message
        TextMessage message = (TextMessage) consumer.receive();
        while (message != null) {
            //提交事务，对于 consumer 来说，即消息被确认
            session.commit();
            System.out.println("接收到消息" + message.getText());
            TimeUnit.SECONDS.sleep(1);
            message = (TextMessage) consumer.receive();
        }
        //关闭所有的 JMS 资源（connection，session，consumer 等）
        consumer.close();
        session.close();
        connection.close();
    }

}