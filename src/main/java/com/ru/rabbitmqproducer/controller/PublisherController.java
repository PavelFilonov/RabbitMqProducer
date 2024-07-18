package com.ru.rabbitmqproducer.controller;

import com.ru.rabbitmqproducer.model.SomeModel;
import com.ru.rabbitmqproducer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublisherController {

	private final ProducerService rabbitMQProducerService;

	@GetMapping("/send")
	public void sendMessage(@RequestBody SomeModel model) {
		rabbitMQProducerService.sendMessage(model.getMessage());
	}

	@GetMapping("/health")
	public String health() {
		return "OK";
	}

}
