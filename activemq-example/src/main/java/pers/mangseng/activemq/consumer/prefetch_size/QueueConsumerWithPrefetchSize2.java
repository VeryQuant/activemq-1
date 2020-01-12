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
public class QueueConsumerWithPrefetchSize2 {

    public static void main(String[] args) throws Exception {
        // 开启 optimizeAcknowledge 批量确认
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("" +
                "tcp://192.168.89.210:61616?jms.optimizeAcknowledge=true&jms.optimizeAcknowledgeTimeOut=10000");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // 必须要设置成 AUTO_ACKNOWLEDGE
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        // 每次预取 100
        Destination destination = session.createQueue("my-queue?consumer.prefetchSize=100");
        MessageConsumer consumer = session.createConsumer(destination);
        TextMessage message = (TextMessage) consumer.receive();
        while (message != null) {
            System.out.println("接收到消息" + message.getText());
            TimeUnit.SECONDS.sleep(1);
            message = (TextMessage) consumer.receive();
        }
        consumer.close();
        session.close();
        connection.close();
    }

}