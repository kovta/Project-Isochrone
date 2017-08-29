package com.kota.stratagem.messageservice.processing;

import com.kota.stratagem.persistence.exception.PersistenceServiceException;

public interface DependencyExtendedDevelopmentProcessor extends DevelopmentProcessor {

	void processConfiguration(String dependant, String dependency) throws PersistenceServiceException;

	void processDeconfiguration(String dependant, String dependency) throws PersistenceServiceException;

}
