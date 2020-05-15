package com.bfxy.rabbitmq.api.message;


import com.bfxy.rabbitmq.api.consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * message及其相关属性和自定义属性
 */
public class Consumer {

	public static void main(String[] args) throws Exception {
		
		//1 创建一个ConnectionFactory, 并进行配置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();
		
		//3 通过connection创建一个Channel
		Channel channel = connection.createChannel();
		
		//4 声明（创建）一个队列
		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);
		//5 创建消费者
		MyConsumer consumer = new MyConsumer(channel);
		
		//6 设置Channel
		channel.basicConsume(queueName, true, consumer);

	}
}
