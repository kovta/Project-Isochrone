package com.kota.stratagem.ejbservice.util;

import com.kota.stratagem.ejbservice.domain.ErrorRepresentor;

public enum ApplicationError {

	UNEXPECTED(10, 500, "Unexpected error"), // Internal Server Error
	NON_EXISTANT(40, 400, "Resource not found"), // Bad Request
	UNAUTHORIZED(30, 401, "Unauthorized"), // Precondition Failed
	HAS_DEPENDENCY(50, 412, "Has dependency"); // Precondition Failed

	private final int code;
	private final int httpStatusCode;
	private final String message;

	private ApplicationError(int code, int httpStatusCode, String message) {
		this.code = code;
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}

	public int getHttpStatusCode() {
		return this.httpStatusCode;
	}

	public ErrorRepresentor build(String field) {
		return new ErrorRepresentor(this.code, this.message, field);
	}

}
