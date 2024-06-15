package com.banking.account.query.api.controllers;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.query.api.dto.AccountLookupResponse;
import com.banking.account.query.api.dto.EqualityType;
import com.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.banking.account.query.api.queries.FindAccountByIdQuery;
import com.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.banking.account.query.api.queries.FindAllAccountsQuery;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping("/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
        
        if (CollectionUtils.isEmpty(accounts)) {
        	return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        
        var response = AccountLookupResponse.builder()
        		.accounts(accounts)
        		.message(MessageFormat.format("%d results", accounts.size()))
        		.build();
        
        return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
    }
    
    @GetMapping("/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getById(@PathVariable String id) {
        List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
        
        if (CollectionUtils.isEmpty(accounts)) {
        	return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        
        var response = AccountLookupResponse.builder()
        		.accounts(accounts)
        		.message(MessageFormat.format("%d results", accounts.size()))
        		.build();
        
        return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
    }
    
    @GetMapping("/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getByAccountHolder(@PathVariable String accountHolder) {
        List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
        
        if (CollectionUtils.isEmpty(accounts)) {
        	return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        
        var response = AccountLookupResponse.builder()
        		.accounts(accounts)
        		.message(MessageFormat.format("%d results", accounts.size()))
        		.build();
        
        return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
    }
    
    @GetMapping("/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getByWithBalance(@PathVariable EqualityType equalityType, @PathVariable Double balance) {
        List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(balance, equalityType));
        
        if (CollectionUtils.isEmpty(accounts)) {
        	return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        
        var response = AccountLookupResponse.builder()
        		.accounts(accounts)
        		.message(MessageFormat.format("%d results", accounts.size()))
        		.build();
        
        return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
    }
}
