package com.kota.stratagem.ejbserviceclient.domain;

public enum ImpedimentStatusRepresentor {

	OPEN("Open"), //
	ADDRESSED("Addressed"), //
	BEING_PROCESSED("Being processed"), //
	SOLUTION_PROVIDED("Solution provided"), //
	DUPLICATE("Duplicate"), //
	DISMISSED("Dismissed"), //
	CONFIRMED("Confirmed");

	private final String label;

	private ImpedimentStatusRepresentor(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}

}
