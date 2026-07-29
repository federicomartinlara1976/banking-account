package com.banking.account.common.events;

import com.banking.cqrs.core.events.BaseEvent;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {

}
