package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class DepositFundsCommand extends BaseCommand {

	@JsonCreator
	public DepositFundsCommand(@JsonProperty("id") String id) {
		super(id);
	}

	@Getter
	@Setter
	@Positive(message = "El depósito debe ser mayor que 0")
	private Double amount;
}
