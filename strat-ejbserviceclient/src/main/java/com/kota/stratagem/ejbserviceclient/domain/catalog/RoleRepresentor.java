package com.kota.stratagem.ejbserviceclient.domain.catalog;

public enum RoleRepresentor {

	PRISTINE_USER("Pristine user", "pristine_user"), //
	GENERAL_USER("General user", "general_user"), //
	GENERAL_MANAGER("General manager", "general_manager"), //
	DEPARTMENT_MANAGER("Department manager", "department_manager"), //
	CENTRAL_MANAGER("Central manager", "central_manager"), //
	SYSTEM_ADMINISTRATOR("System administrator", "system_administrator");

	static {
		PRISTINE_USER.subordinates = new RoleRepresentor[] {};
		GENERAL_USER.subordinates = new RoleRepresentor[] { PRISTINE_USER };
		GENERAL_MANAGER.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER };
		DEPARTMENT_MANAGER.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER, GENERAL_MANAGER };
		CENTRAL_MANAGER.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER, GENERAL_MANAGER, DEPARTMENT_MANAGER };
		SYSTEM_ADMINISTRATOR.subordinates = new RoleRepresentor[] { PRISTINE_USER, GENERAL_USER, GENERAL_MANAGER, DEPARTMENT_MANAGER, CENTRAL_MANAGER };
	}

	private final String label;

	private final String title;

	private RoleRepresentor[] subordinates;

	private RoleRepresentor(String label, String title) {
		this.label = label;
		this.title = title;
	}

	public String getLabel() {
		return this.label;
	}

	public String getTitle() {
		return this.title;
	}

	public String getName() {
		return this.name();
	}

	public RoleRepresentor[] getSubordinateRoles() {
		return this.subordinates;
	}

}
