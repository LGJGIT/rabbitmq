package com.bfxy.rabbitmq.api.exchange.direct;

import com.bfxy.rabbitmq.api.consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Direct Exchange
 * 	所有发送到Direct Exchange消息将被转发到RouteKey中指定的Queue
 * 	生产者中设置的 routingKey 必须要和消费者中设置的routingKey 一致
 * @author lvguojun
 */
public class Consumer4DirectExchange {

	public static void main(String[] args) throws Exception {
		
		
        ConnectionFactory connectionFactory = new ConnectionFactory() ;  
        
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        
        Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_direct_exchange";
		String exchangeType = "direct";
		String queueName = "test_direct_queue";
		String routingKey = "test.direct";
		
		//表示声明了一个交换机
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		//表示声明了一个队列
		channel.queueDeclare(queueName, true, false, false, null);
		//建立一个绑定关系:
		channel.queueBind(queueName, exchangeName, routingKey);
		
        //durable 是否持久化消息
        MyConsumer consumer = new MyConsumer(channel);
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, consumer);  
	}
}
