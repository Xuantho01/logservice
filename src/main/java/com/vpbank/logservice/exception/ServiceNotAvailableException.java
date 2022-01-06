package com.vpbank.logservice.exception;


public class ServiceNotAvailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceNotAvailableException(String msg) {
		super(msg);
	}

	public ServiceNotAvailableException(String msg, Exception ex) {
		super(msg, ex);
	}
}
