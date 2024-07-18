package com.ru.rabbitmqproducer.config;

import lombok.val;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {

	@Value("${app.rabbitmq.username}")
	private String username;

	@Value("${app.rabbitmq.password}")
	private String password;

	@Value("${app.rabbitmq.virtual-host}")
	private String virtualHostName;

	@Bean
	public ConnectionFactory connectionFactory() {
		val cachingConnectionFactory = new CachingConnectionFactory("localhost");
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setPassword(password);
		cachingConnectionFactory.setVirtualHost(virtualHostName);
		return cachingConnectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(connectionFactory());
	}

	@Bean
	public Queue myQueue(@Value("${app.rabbitmq.queue}") String queueName) {
		return new Queue(queueName);
	}

	@Bean
	DirectExchange exchange(@Value("${app.rabbitmq.exchange}") String exchangeName) {
		return new DirectExchange(exchangeName, true, false);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange, @Value("${app.rabbitmq.rooting-key}") String rootingKey) {
		return BindingBuilder.bind(queue).to(exchange).with(rootingKey);
	}

}
