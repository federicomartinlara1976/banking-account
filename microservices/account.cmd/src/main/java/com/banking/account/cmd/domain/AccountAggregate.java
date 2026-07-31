package com.banking.account.cmd.domain;

import java.util.Date;

import org.springframework.util.Assert;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.cqrs.core.domain.AggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
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
		Assert.isTrue(active, "Los fondos no pueden ser depositados en esta cuenta");
		
		FundsDepositedEvent event = new FundsDepositedEvent();
		
		event.setId(id);
		event.setAmount(amount);
		
		raiseEvent(event);
	}
	
	public void withdrawFunds(Double amount) {
		Assert.isTrue(active, "La cuenta bancaria está cerrada");
		
		FundsWithdrawnEvent event = new FundsWithdrawnEvent();
		
		event.setId(id);
		event.setAmount(amount);
		
		raiseEvent(event);
	}
	
	@SneakyThrows(IllegalStateException.class)
	public void closeAccount() {
		Assert.isTrue(active, "La cuenta bancaria está cerrada");
		
		AccountClosedEvent event = new AccountClosedEvent();
		
		event.setId(id);
		
		raiseEvent(event);
	}
	
	public void apply(AccountOpenedEvent event) {
		log.info("Apply {}", event);
		this.id = event.getId();
		this.active = Boolean.TRUE;
		this.balance = event.getOpeningBalance();
	}
	
	public void apply(FundsDepositedEvent event) {
		log.info("Apply {}", event);
		this.id = event.getId();
		this.balance += event.getAmount();
	}
	
	public void apply(FundsWithdrawnEvent event) {
		log.info("Apply {}", event);
		this.id = event.getId();
		this.balance -= event.getAmount();
	}
	
	public void apply(AccountClosedEvent event) {
		log.info("Apply {}", event);
		this.id = event.getId();
	}
}
