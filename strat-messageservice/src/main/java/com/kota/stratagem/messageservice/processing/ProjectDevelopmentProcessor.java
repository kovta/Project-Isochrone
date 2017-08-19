package com.kota.stratagem.messageservice.processing;

import javax.ejb.Local;

@Local
public interface ProjectDevelopmentProcessor {

	void processCreation(String message);

	void processAssignment(String message);

	void processModification(String message);

	void processDeletion(String message);

}
