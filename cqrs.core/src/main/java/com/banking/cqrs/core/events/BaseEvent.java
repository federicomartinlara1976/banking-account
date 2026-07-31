package com.banking.cqrs.core.events;

import com.banking.cqrs.core.messages.Message;

import lombok.Getter;
import lombok.Setter;

public class BaseEvent extends Message {

	@Getter
	@Setter
	private Integer version;
}
