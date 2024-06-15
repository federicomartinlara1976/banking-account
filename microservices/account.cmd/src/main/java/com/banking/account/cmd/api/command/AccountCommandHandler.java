package com.banking.account.cmd.api.command;

import org.apache.kafka.common.errors.IllegalSaslStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.handlers.EventSourcingHandler;

@Service
public class AccountCommandHandler implements CommandHandler {

	@Autowired
	private EventSourcingHandler<AccountAggregate> handler;
	
	@Override
	public void handle(OpenAccountCommand command) {
		var aggregate = new AccountAggregate(command);
		handler.save(aggregate);
	}

	@Override
	public void handle(DepositFundsCommand command) {
		var aggregate = handler.getById(command.getId());
		aggregate.depositFunds(command.getAmount());
		handler.save(aggregate);
	}

	@Override
	public void handle(WithdrawFundsCommand command) {
		var aggregate = handler.getById(command.getId());
		
		if (command.getAmount() > aggregate.getBalance()) {
			throw new IllegalSaslStateException("Insuficientes fondos");
		}
		
		aggregate.withdrawFunds(command.getAmount());
		handler.save(aggregate);
	}

	@Override
	public void handle(CloseAccountCommand command) {
		var aggregate = handler.getById(command.getId());
		aggregate.closeAccount();
		handler.save(aggregate);
	}
}
