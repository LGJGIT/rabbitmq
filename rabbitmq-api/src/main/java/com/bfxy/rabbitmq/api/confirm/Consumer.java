package com.bfxy.rabbitmq.api.confirm;

import com.bfxy.rabbitmq.api.consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * confirm确认机制
 */
public class Consumer {

	
	public static void main(String[] args) throws Exception {
		
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 获取Connection
		Connection connection = connectionFactory.newConnection();
		
		//3 通过Connection创建一个新的Channel
		Channel channel = connection.createChannel();
		
		String exchangeName = "test_confirm_exchange";
		//# : 匹配一个或者多个词
		String routingKey = "confirm.#";
		String queueName = "test_confirm_queue";
		
		//4 声明交换机和队列 然后进行绑定设置, 最后指定路由Key
		channel.exchangeDeclare(exchangeName, "topic", true);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		//5 创建消费者 
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
