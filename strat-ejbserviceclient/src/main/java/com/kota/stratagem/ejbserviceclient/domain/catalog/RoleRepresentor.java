package com.kota.stratagem.ejbserviceclient.domain.catalog;

public enum RoleRepresentor {

	PRISTINE_USER("Pristine user"), //
	GENERAL_USER("General user"), //
	GENERAL_MANAGER("General manager"), //
	DEPARTMENT_MANAGER("Department manager"), //
	CENTRAL_MANAGER("Central manager"), //
	SYSTEM_ADMINISTRATOR("System administrator");

	static {
		PRISTINE_USER.subordinates = new RoleRepresentor[] {};
		GENERAL_USER.subordinates = new RoleRepresentor[] { PRISTINE_USER };
		GENERAL_MANAGER.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER };
		DEPARTMENT_MANAGER.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER, GENERAL_MANAGER };
		CENTRAL_MANAGER.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER, GENERAL_MANAGER, DEPARTMENT_MANAGER };
		SYSTEM_ADMINISTRATOR.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER, GENERAL_MANAGER, DEPARTMENT_MANAGER, CENTRAL_MANAGER };
	}

	private final String label;

	private RoleRepresentor[] subordinates;

	private RoleRepresentor(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name();
	}

	public RoleRepresentor[] getSubordinateRoles() {
		return this.subordinates;
	}

}
