package com.kota.stratagem.ejbserviceclient.domain;

public enum RoleRepresentor {

	PRISTINE_USER("Pristine user"), //
	GENERAL_USER("General user"), //
	GENERAL_MANAGER("General manager"), //
	DEPARTMENT_MANAGER("Department manager"), //
	CENTRAL_MANAGER("Central manager"), //
	SYSTEM_ADMINISTRATOR("System administrator");

	private final String label;

	private RoleRepresentor(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}

}
