package com.banking.account.cmd.domain;

import java.util.Date;

import org.apache.kafka.common.errors.IllegalSaslStateException;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.cqrs.core.domain.AggregateRoot;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class AccountAggregate extends AggregateRoot {
	
	private Boolean active;
	
	private Double balance;
	
	public AccountAggregate(OpenAccountCommand command) {
		raiseEvent(AccountOpenedEvent.builder()
				.id(command.getId())
				.accountHolder(command.getAccountHolder())
				.createdDate(new Date())
				.accountType(command.getAccountType())
				.openingBalance(command.getOpeningBalance())
				.build());
	}
	
	public void depositFunds(Double amount) {
		if (!active) {
			throw new IllegalSaslStateException("Los fondos no pueden ser depositados en esta cuenta");
		}
		
		if (amount <= 0.0) {
			throw new IllegalSaslStateException("El depósito de dinero no puede ser menor o igual a 0");
		}
		
		raiseEvent(FundsDepositedEvent.builder()
				.id(this.id)
				.amount(amount)
				.build());
	}
	
	public void withdrawFunds(Double amount) {
		if (!active) {
			throw new IllegalSaslStateException("La cuenta bancaria está cerrada");
		}
		
		raiseEvent(FundsWithdrawnEvent.builder()
				.id(this.id)
				.amount(amount)
				.build());
	}
	
	public void closeAccount() {
		if (!active) {
			throw new IllegalSaslStateException("La cuenta bancaria está cerrada");
		}
		
		raiseEvent(AccountClosedEvent.builder()
				.id(this.id)
				.build());
	}
	
	public void apply(AccountOpenedEvent event) {
		log.info("Apply {}", event.toString());
		this.id = event.getId();
		this.active = Boolean.TRUE;
		this.balance = event.getOpeningBalance();
	}
	
	public void apply(FundsDepositedEvent event) {
		log.info("Apply {}", event.toString());
		this.id = event.getId();
		this.balance += event.getAmount();
	}
	
	public void apply(FundsWithdrawnEvent event) {
		log.info("Apply {}", event.toString());
		this.id = event.getId();
		this.balance -= event.getAmount();
	}
	
	public void apply(AccountClosedEvent event) {
		log.info("Apply {}", event.toString());
		this.id = event.getId();
		this.active = Boolean.FALSE;
	}
}
