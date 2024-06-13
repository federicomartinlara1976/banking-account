package com.banking.account.cmd.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banking.cqrs.core.events.EventModel;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, String> {

	List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
