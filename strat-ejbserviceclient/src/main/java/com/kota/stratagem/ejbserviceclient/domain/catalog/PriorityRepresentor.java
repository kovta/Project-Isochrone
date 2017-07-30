package com.kota.stratagem.ejbserviceclient.domain;

public enum PriorityRepresentor {

	LOW("Low"), //
	MEDIUM("Medium"), //
	HIGH("High"), //
	VERY_HIGH("Very High");

	private final String label;

	private PriorityRepresentor(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}

}
