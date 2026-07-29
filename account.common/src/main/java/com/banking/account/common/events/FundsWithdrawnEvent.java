package com.banking.account.common.events;

import com.banking.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundsWithdrawnEvent extends BaseEvent {
	
	private Double amount;
}
