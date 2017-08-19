package com.kota.stratagem.messageservice.processing;

import javax.ejb.Stateless;

@Stateless(mappedName = "ejb/projectProcessor")
public class ProjectDevelopmentProcessorImpl implements ProjectDevelopmentProcessor {

	@Override
	public void processCreation(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processAssignment(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processModification(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processDeletion(String message) {
		// TODO Auto-generated method stub

	}

}
