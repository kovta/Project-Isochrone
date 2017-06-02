package com.kota.stratagem.ejbservice.domain;

public class ErrorRepresentor {

	private int code;

	private String message;

	private String fields;

	public ErrorRepresentor() {
		this(0, null, null);
	}

	public ErrorRepresentor(final int code, final String message, final String fields) {
		this.code = code;
		this.message = message;
		this.fields = fields;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public String getFields() {
		return this.fields;
	}

	public void setCode(final int code) {
		this.code = code;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setFields(final String fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "ErrorRepresentor [code=" + this.code + ", message=" + this.message + ", fields=" + this.fields + "]";
	}

}