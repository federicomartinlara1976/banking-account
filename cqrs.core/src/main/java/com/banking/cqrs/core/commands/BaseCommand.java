package com.banking.cqrs.core.commands;

import com.banking.cqrs.core.messages.Message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class BaseCommand extends Message {
	
	public BaseCommand(String id) {
		super(id);
	}

}
