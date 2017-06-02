package com.kota.stratagem.persistence.exception;

import com.kota.stratagem.persistence.util.PersistenceApplicationError;

public class CoherentPersistenceServiceException extends PersistenceServiceException {

	private static final long serialVersionUID = 1207428295818535206L;

	private final PersistenceApplicationError error;
	private final String field;

	public CoherentPersistenceServiceException(final PersistenceApplicationError error, final String message, final String field) {
		super(message);
		this.error = error;
		this.field = field;
	}

	public PersistenceApplicationError getError() {
		return this.error;
	}

	public String getField() {
		return this.field;
	}

}