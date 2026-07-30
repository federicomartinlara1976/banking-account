package com.banking.account.common.events;

import com.banking.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FundsWithdrawnEvent extends BaseEvent {
	
	@Getter
	@Setter
	private Double amount;
}
