package com.banking.account.query.api.queries;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.query.api.dto.EqualityType;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.domain.BaseEntity;

@Service
public class AccountQueryHandler implements QueryHandler {
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<BaseEntity> handle(FindAllAccountsQuery query) {
		Iterable<BankAccount> accounts = accountRepository.findAll();
		List<BaseEntity> bankAccountList = new ArrayList<>();
		accounts.forEach(bankAccountList::add);
		return bankAccountList;
	}

	@Override
	public List<BaseEntity> handle(FindAccountByIdQuery query) {
		var bankAccount = accountRepository.findById(query.getId());
		if (bankAccount.isPresent()) {
			return null;
		}
		List<BaseEntity> accountList = new ArrayList<>();
		accountList.add(bankAccount.get());
		return accountList;
	}

	@Override
	public List<BaseEntity> handle(FindAccountByHolderQuery query) {
		var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
		if (bankAccount.isPresent()) {
			return null;
		}
		List<BaseEntity> accountList = new ArrayList<>();
		accountList.add(bankAccount.get());
		return accountList;
	}

	@Override
	public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
		List<BaseEntity> bankAccountList = (query.getEqualityType() == EqualityType.GREATER_THAN) 
				? accountRepository.findByBalanceGreaterThan(query.getBalance()) 
				: accountRepository.findByBalanceLessThan(query.getBalance());
		return bankAccountList;
	}

}
