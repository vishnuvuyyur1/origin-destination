package com.klm.cases.df.exception;

public class OriginalCaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OriginalCaseException(String errorMessage, Exception e) {
		super(errorMessage, e);
	}

	public OriginalCaseException(String errorMessage) {
		super(errorMessage);
	}
}
