package com.kota.stratagem.messageservice.processing;

import javax.ejb.Local;

@Local
public interface SubmoduleDevelopmentProcessor {

	void processCreation(String message);

	void processAssignment(String message);

	void processModification(String message);

	void processDeletion(String message);

}