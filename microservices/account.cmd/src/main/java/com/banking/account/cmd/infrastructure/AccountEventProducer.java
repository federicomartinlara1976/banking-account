package com.banking.account.cmd.infrastructure;

import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.producers.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        log.info("Send event: {}", event.toString());
        kafkaTemplate.send(topic, event);
    }
}
