package edu.esi.uclm.exceptions;

import org.springframework.http.HttpStatus;

public class SigevaException extends Exception {

	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
	private final String message;

	public SigevaException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
