package com.ru.rabbitmqproducer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

	private final RabbitTemplate rabbitTemplate;

	@Value("${app.rabbitmq.rooting-key}")
	private String routingKey;

	@Value("${app.rabbitmq.exchange}")
	private String exchangeName;

	@Override
	public void sendMessage(String message) {
		rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
	}

}
