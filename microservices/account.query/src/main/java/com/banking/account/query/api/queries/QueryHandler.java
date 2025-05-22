package com.banking.account.query.api.queries;

import java.util.List;

import com.banking.cqrs.core.domain.BaseEntity;

public interface QueryHandler {

	List<BaseEntity> handle(FindAllAccountsQuery query);
	
	List<BaseEntity> handle(FindAccountByIdQuery query);
	
	List<BaseEntity> handle(FindAccountByHolderQuery query);
	
	List<BaseEntity> handle(FindAccountWithBalanceQuery query);
}
