package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;

import lombok.Getter;
import lombok.Setter;

public class WithdrawFundsCommand extends BaseCommand {

	public WithdrawFundsCommand(String id) {
		super(id);
	}

	@Getter
	@Setter
	private Double amount;
}
