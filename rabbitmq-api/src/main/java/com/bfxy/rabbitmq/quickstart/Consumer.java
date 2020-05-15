package com.bfxy.rabbitmq.quickstart;

import com.bfxy.rabbitmq.api.consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

@SuppressWarnings("all")
public class Consumer {

	public static void main(String[] args) throws Exception {
		
		//1 创建一个ConnectionFactory, 并进行配置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");


		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		//3 通过connection创建一个Channel
		Channel channel = connection.createChannel();
		//4 声明（创建）一个队列
		String queueName = "test001";
		/**
		 * String queue, 队列名称
		 * boolean durable, 队列是否持久化
		 * boolean exclusive, 是否独占
		 * boolean autoDelete, 是否自动删除
		 * Map<String, Object> arguments  参数
		 */
		channel.queueDeclare(queueName, true, false, false, null);
		
		//5 创建消费者
//		DefaultConsumer defaultConsumer = new DefaultConsumer(channel);
		//QueueingConsumer 已过时
//		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		MyConsumer queueingConsumer = new MyConsumer(channel);
		
		//6 设置Channel获取消息
		channel.basicConsume(queueName, true, queueingConsumer);
		
//		while(true){
//			//7 获取消息
//			Delivery delivery = queueingConsumer.nextDelivery();
//			String msg = new String(delivery.getBody());
//			System.err.println("消费端: " + msg);
//			//Envelope envelope = delivery.getEnvelope();
//		}
		
	}
}
