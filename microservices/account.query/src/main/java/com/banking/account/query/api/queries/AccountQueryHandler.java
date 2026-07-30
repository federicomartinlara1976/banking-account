package com.banking.account.query.api.queries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.account.query.api.dto.EqualityType;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.domain.BaseEntity;

@Service
public class AccountQueryHandler implements QueryHandler {
	
	private AccountRepository accountRepository;
		
	public AccountQueryHandler(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public List<BaseEntity> handle(FindAllAccountsQuery query) {
		Iterable<BankAccount> accounts = accountRepository.findAll();
		List<BaseEntity> bankAccountList = new ArrayList<>();
		accounts.forEach(bankAccountList::add);
		return bankAccountList;
	}

	@Override
	public List<BaseEntity> handle(FindAccountByIdQuery query) {
		return accountRepository.findById(query.getId())
	            .map(Collections::<BaseEntity>singletonList)
	            .orElse(Collections.emptyList());
	}

	@Override
	public List<BaseEntity> handle(FindAccountByHolderQuery query) {
		return accountRepository.findByAccountHolder(query.getAccountHolder())
	            .map(Collections::<BaseEntity>singletonList)
	            .orElse(Collections.emptyList());
	}

	@Override
	public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
		return (query.getEqualityType() == EqualityType.GREATER_THAN) 
				? accountRepository.findByBalanceGreaterThan(query.getBalance()) 
				: accountRepository.findByBalanceLessThan(query.getBalance());
	}

}
