package com.banking.account.query.api.queries;

import com.banking.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {

	@Getter
	@Setter
	private String id;

}
