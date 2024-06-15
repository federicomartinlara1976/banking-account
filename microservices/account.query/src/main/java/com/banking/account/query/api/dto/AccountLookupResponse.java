package com.banking.account.query.api.dto;

import java.util.List;

import com.banking.account.common.dto.BaseResponse;
import com.banking.account.query.domain.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountLookupResponse extends BaseResponse {

	private List<BankAccount> accounts;
	
	public AccountLookupResponse(String message) {
		super(message);
	}
}
