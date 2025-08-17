package com.banking.account.query.infrastructure.handlers;

import java.util.function.DoubleBinaryOperator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.events.BaseEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountEventHandler implements EventHandler {

    private AccountRepository accountRepository;
    
    DoubleBinaryOperator bSuma = (current, amount) -> current + amount;
    DoubleBinaryOperator bResta = (current, amount) -> current - amount;

    public AccountEventHandler(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
    @Transactional
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        var saved = accountRepository.save(bankAccount);
        log.info("Saved: {}", saved);
    }

    @Override
    public void on(FundsDepositedEvent event) {
    	update(event, event.getAmount(), bSuma);
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
    	update(event, event.getAmount(), bResta);
    }
    
    @Transactional
    private void update(BaseEvent event, Double amount, DoubleBinaryOperator operation) {
    	accountRepository.findById(event.getId()).ifPresent(account -> {
        	var currentBalance = account.getBalance();
            var latestBalance = operation.applyAsDouble(currentBalance, amount);
            account.setBalance(latestBalance);

            var updated = accountRepository.save(account);
            log.info("Updated: {}", updated.toString());
        });
    }
    
    @Override
    @Transactional
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
        log.info("Deleted: {}", event.getId());
    }
}
