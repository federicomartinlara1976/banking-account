package com.banking.cqrs.core.commands;

import com.banking.cqrs.core.messages.Message;

public class BaseCommand extends Message {
	
	public BaseCommand(String id) {
		super(id);
	}

}
