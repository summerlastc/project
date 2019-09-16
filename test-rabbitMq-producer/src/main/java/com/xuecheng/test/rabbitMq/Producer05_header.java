package com.xuecheng.test.rabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Producer05_header {

    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_HEADERSS_INFORM="exchange_headerss_inform";
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //创建一个与MQ的连接
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器

            //创建一个连接
            connection = factory.newConnection();
            //创建与交换机的通道，每个通道代表一个会话
            channel = connection.createChannel();
            //声明交换机 String exchange, BuiltinExchangeType type
            /**
             * 参数明细
             * 1、交换机名称
             * 2、交换机类型，fanout、topic、direct、HEADERSS
             */
            channel.exchangeDeclare(EXCHANGE_HEADERSS_INFORM, BuiltinExchangeType.HEADERS);
            //声明队列
            /**
             * 参数明细：
             * 1、队列名称
             * 2、是否持久化
             * 3、是否独占此队列
             * 4、队列不用是否自动删除
             * 5、参数
             */

            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            Map<String, Object> HEADERSS_email = new Hashtable<String, Object>();
            HEADERSS_email.put("inform_type", "email");
            Map<String, Object> HEADERSS_sms = new Hashtable<String, Object>();
            HEADERSS_sms.put("inform_type", "sms");
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_HEADERSS_INFORM,"",HEADERSS_email);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_HEADERSS_INFORM,"",HEADERSS_sms);





            //发送邮件消息
            for (int i=0;i<10;i++){
                String message = "email inform to user"+i;
                Map<String,Object> HEADERSS =  new Hashtable<String, Object>();
                HEADERSS.put("inform_type", "email");//匹配email通知消费者绑定的header
                    //HEADERSS.put("inform_type", "sms");//匹配sms通知消费者绑定的header
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                properties.headers(HEADERSS);
                        //Email通知
                channel.basicPublish(EXCHANGE_HEADERSS_INFORM, "", properties.build(), message.getBytes());




                //向交换机发送消息 String exchange, String routingKey, BasicProperties props, byte[] body
                /**
                 * 参数明细
                 * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
                 * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消息将发到此队列
                 * 3、消息属性
                 * 4、消息内容
                 */

                System.out.println("Send Message is:'" + message + "'");
            }
            //发送短信消息
            for (int i=0;i<10;i++){
                String message = "sms inform to user"+i;
                Map<String,Object> headerss =  new Hashtable<String, Object>();
                headerss.put("inform_type", "sms");//匹配email通知消费者绑定的header
                //HEADERSS.put("inform_type", "sms");//匹配sms通知消费者绑定的header
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                properties.headers(headerss);
                //Email通知
                channel.basicPublish(EXCHANGE_HEADERSS_INFORM, "", properties.build(), message.getBytes());



                System.out.println("Send Message is:'" + message + "'");
            }
            //发送短信和邮件消息
            for (int i=0;i<10;i++){
                String message = "sms and email inform to user"+i;
                Map<String,Object> HEADERSS =  new Hashtable<String, Object>();
                HEADERSS.put("inform_type", "sms_email");//匹配email通知消费者绑定的header
                //HEADERSS.put("inform_type", "sms");//匹配sms通知消费者绑定的header
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                properties.headers(HEADERSS);
                //Email通知
                channel.basicPublish(EXCHANGE_HEADERSS_INFORM, "", properties.build(), message.getBytes());


                System.out.println("Send Message is:'" + message + "'");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally{
            if(channel!=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }




}
