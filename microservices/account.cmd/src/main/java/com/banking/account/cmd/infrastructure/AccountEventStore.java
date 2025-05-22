package com.banking.account.cmd.infrastructure;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.banking.cqrs.core.producers.EventProducer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.events.EventModel;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.exceptions.ConcurrencyException;
import com.banking.cqrs.core.infrastructure.EventStore;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountEventStore implements EventStore {
	
	@Autowired
	private EventStoreRepository eventStoreRepository;

	@Autowired
	private EventProducer eventProducer;

	@Override
	public void saveEvents(String aggregateId, Iterable<BaseEvent> events, Integer expectedVersion) {
		var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
		if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
			throw new ConcurrencyException();
		}
		
		var version = expectedVersion;
		for (var event : events) {
			version++;
			event.setVersion(version);
			var eventModel = EventModel.builder()
					.timeStamp(new Date())
					.aggregateIdentifier(aggregateId)
					.aggregateType(AccountAggregate.class.getTypeName())
					.version(version)
					.eventType(event.getClass().getTypeName())
					.eventData(event)
					.build();
			
			var persistedEvent = eventStoreRepository.save(eventModel);
			if (!Objects.isNull(persistedEvent) && !persistedEvent.getId().isEmpty()) {
				log.info("Saved: {}", persistedEvent.toString());

				// Producir evento para Kafka
				eventProducer.produce(event.getClass().getSimpleName(), event);
			}
		}
	}

	@Override
	public List<BaseEvent> getEvents(String aggregateId) {
		var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
		if (CollectionUtils.isEmpty(eventStream)) {
			throw new AggregateNotFoundException("La cuenta del banco es incorrecta");
		}
		
		return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
	}

}
