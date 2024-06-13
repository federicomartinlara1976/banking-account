package com.banking.cqrs.core.events;

import com.banking.cqrs.core.messages.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEvent extends Message {

	private Integer version;
}
