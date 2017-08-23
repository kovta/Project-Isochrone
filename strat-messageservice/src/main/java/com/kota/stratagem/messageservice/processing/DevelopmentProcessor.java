package com.kota.stratagem.messageservice.processing;

import com.kota.stratagem.persistence.exception.PersistenceServiceException;

public interface DevelopmentProcessor {

	void processCreation(String message) throws PersistenceServiceException;

	void processAssignment(String message) throws PersistenceServiceException;

	void processDissociation(String message) throws PersistenceServiceException;

	void processModification(String originMessage, String resultMessage) throws PersistenceServiceException;

	void processDeletion(String message, String operator) throws PersistenceServiceException;

}
