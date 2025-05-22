package com.banking.cqrs.core.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class AggregateNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 365392718071135723L;

}
