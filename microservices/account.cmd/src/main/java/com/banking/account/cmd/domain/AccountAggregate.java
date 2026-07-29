package com.banking.account.cmd.domain;

import java.util.Date;

import org.apache.kafka.common.errors.IllegalSaslStateException;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.cqrs.core.domain.AggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class AccountAggregate extends AggregateRoot {
	
	@Getter
	private Boolean active;
	
	@Getter
	private Double balance;
	
	public AccountAggregate(OpenAccountCommand command) {
		AccountOpenedEvent event = new AccountOpenedEvent();
		
		event.setId(command.getId());
		event.setAccountHolder(command.getAccountHolder());
		event.setCreatedDate(new Date());
		event.setAccountType(command.getAccountType());
		event.setOpeningBalance(command.getOpeningBalance());
		
		raiseEvent(event);
	}
	
	public void depositFunds(Double amount) {
		if (!active) {
			throw new IllegalSaslStateException("Los fondos no pueden ser depositados en esta cuenta");
		}
		
		if (amount <= 0.0) {
			throw new IllegalSaslStateException("El depósito de dinero no puede ser menor o igual a 0");
		}
		
		FundsDepositedEvent event = new FundsDepositedEvent();
		
		event.setId(id);
		event.setAmount(amount);
		
		raiseEvent(event);
	}
	
	public void withdrawFunds(Double amount) {
		if (!active) {
			throw new IllegalSaslStateException("La cuenta bancaria está cerrada");
		}
		
		FundsWithdrawnEvent event = new FundsWithdrawnEvent();
		
		event.setId(id);
		event.setAmount(amount);
		
		raiseEvent(event);
	}
	
	public void closeAccount() {
		if (!active) {
			throw new IllegalSaslStateException("La cuenta bancaria está cerrada");
		}
		
		AccountClosedEvent event = new AccountClosedEvent();
		
		event.setId(id);
		
		raiseEvent(event);
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
