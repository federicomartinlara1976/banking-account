package com.banking.account.query.infrastructure.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.account.query.infrastructure.handlers.EventHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountEventConsumer implements EventConsumer {

    private EventHandler eventHandler;

    public AccountEventConsumer(EventHandler eventHandler) {
		super();
		this.eventHandler = eventHandler;
	}

	@KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void comsume(AccountOpenedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void comsume(FundsDepositedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void comsume(FundsWithdrawnEvent event, Acknowledgment acknowledgment) {
    	try {
    		eventHandler.on(event);
    	} catch (IllegalArgumentException e) { // Si la cuenta no tiene fondos suficientes, lanzará esta excepción
    		log.warn(e.getMessage());
    	} finally {
    		acknowledgment.acknowledge();
    	}
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void comsume(AccountClosedEvent event, Acknowledgment acknowledgment) {
    	try {
    		eventHandler.on(event);
    	} catch (IllegalArgumentException e) { // Si la cuenta no está vacía, lanzará esta excepción
    		log.warn(e.getMessage());
    	} finally {
    		acknowledgment.acknowledge();
    	}
    }
}
