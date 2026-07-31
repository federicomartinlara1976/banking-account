package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;

import lombok.Getter;
import lombok.Setter;

public class DepositFundsCommand extends BaseCommand {

	public DepositFundsCommand(String id) {
		super(id);
	}

	@Getter
	@Setter
	private Double amount;
}
