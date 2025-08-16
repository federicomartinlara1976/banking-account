package com.banking.account.query.infrastructure.handlers;

import java.util.function.BinaryOperator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountEventHandler implements EventHandler {

    private AccountRepository accountRepository;
    
    BinaryOperator<Double> bSuma = (current, amount) -> current + amount;
    BinaryOperator<Double> bResta = (current, amount) -> current - amount;

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
        log.info("Saved: {}", saved.toString());
    }

    @Override
    @Transactional
    public void on(FundsDepositedEvent event) {
    	updateFunds(event, bSuma);
    }

    @Override
    @Transactional
    public void on(FundsWithdrawnEvent event) {
    	updateFunds(event, bResta);
    }
    
    private void updateFunds(FundsEvent event, BinaryOperator<Double> operation) {
    	accountRepository.findById(event.getId()).ifPresent(account -> {
        	var currentBalance = account.getBalance();
            var latestBalance = operation.apply(currentBalance, event.getAmount());
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
