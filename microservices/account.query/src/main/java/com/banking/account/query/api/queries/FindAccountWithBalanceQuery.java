package com.banking.account.query.api.queries;

import com.banking.account.query.api.dto.EqualityType;
import com.banking.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

	@Getter
	@Setter
	private Double balance;
	
	@Getter
	@Setter
	private EqualityType equalityType;
}
