package com.banking.cqrs.core.events;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Document(collection = "eventStore")
public class EventModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7160136957030951070L;

	@Id
	private String id;
	
	private Date timeStamp;
	
	private String aggregateIdentifier;
	
	private String aggregateType;
	
	private Integer version;
	
	private String eventType;
	
	private BaseEvent eventData;
}
