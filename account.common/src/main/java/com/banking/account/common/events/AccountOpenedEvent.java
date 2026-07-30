package com.banking.account.common.events;

import java.util.Date;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountOpenedEvent extends BaseEvent {

	@Getter
	@Setter
	private String accountHolder;
	
	@Getter
	@Setter
	private AccountType accountType;
	
	@Getter
	@Setter
	private Date createdDate;
	
	@Getter
	@Setter
	private Double openingBalance;

}
