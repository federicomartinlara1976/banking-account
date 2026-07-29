package com.banking.account.cmd.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.banking.cqrs.core.events.EventModel;

public interface EventStoreRepository extends MongoRepository<EventModel, String> {

	@Query("{ 'aggregateIdentifier' : ?0 }")
	List<EventModel> listByIdentifier(String aggregateIdentifier);
}
