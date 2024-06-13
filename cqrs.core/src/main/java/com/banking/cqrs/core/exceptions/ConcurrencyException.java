package com.banking.cqrs.core.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class ConcurrencyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1317594937556122517L;

}
