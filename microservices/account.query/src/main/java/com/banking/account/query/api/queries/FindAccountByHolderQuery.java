package com.banking.account.query.api.queries;

import com.banking.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {
	
	@Getter
	@Setter
	private String accountHolder;
}
