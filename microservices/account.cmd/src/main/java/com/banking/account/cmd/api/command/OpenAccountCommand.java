package com.banking.account.cmd.api.command;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.commands.BaseCommand;

import lombok.Getter;
import lombok.Setter;

public class OpenAccountCommand extends BaseCommand {
	
	public OpenAccountCommand(String id) {
		super(id);
	}

	@Getter
	@Setter
	private String accountHolder;
	
	@Getter
	@Setter
	private AccountType accountType;
	
	@Getter
	@Setter
	private Double openingBalance;

}
