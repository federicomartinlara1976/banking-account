package com.banking.account.query.api.dto;

import java.util.List;

import com.banking.account.common.dto.BaseResponse;
import com.banking.account.query.domain.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse extends BaseResponse {

	@Getter
	@Setter
	private List<BankAccount> accounts;
	
	public AccountLookupResponse(String message) {
		super(message);
	}
}
