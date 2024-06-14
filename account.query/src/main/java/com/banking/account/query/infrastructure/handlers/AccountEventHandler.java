package com.banking.account.query.infrastructure.handlers;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private AccountRepository accountRepository;

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

        accountRepository.save(bankAccount);
    }

    @Override
    @Transactional
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if (!bankAccount.isPresent()) {
            return;
        }

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    @Transactional
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if (!bankAccount.isPresent()) {
            return;
        }

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    @Transactional
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
