package com.kota.stratagem.ejbserviceclient.domain;

public enum ObjectiveStatusRepresentor {

	PLANNED("Planned"), //
	DESIGNATED("Designated"), //
	CONTINUOUS("Continuous"), //
	DISCONTINUED("Discontinued"), //
	COMPLETED("Completed");

	private final String label;

	private ObjectiveStatusRepresentor(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}

}
