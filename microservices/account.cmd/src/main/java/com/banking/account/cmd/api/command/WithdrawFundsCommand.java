package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class WithdrawFundsCommand extends BaseCommand {

	@JsonCreator
	public WithdrawFundsCommand(@JsonProperty("id") String id) {
		super(id);
	}

	@Getter
	@Setter
	@Positive(message = "La retirada debe ser mayor que 0")
	private Double amount;
}
