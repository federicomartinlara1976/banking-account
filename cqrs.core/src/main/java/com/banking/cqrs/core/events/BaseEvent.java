package com.banking.cqrs.core.events;

import com.banking.cqrs.core.messages.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEvent extends Message {

	private Integer version;
}
