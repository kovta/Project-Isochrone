package com.kota.stratagem.security.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Authorizations {

	public static final String[] SYSTEM_ADMINISTRATOR_LEVEL = PopulateSystemAdministratorAuthorizationArray();

	public static final String[] CENTRAL_MANAGER_LEVEL = PopulateCentralManagerAuthorizationArray();

	public static final String[] DEPARTMENT_MANAGER_LEVEL = PopulateDepartmentManagerAuthorizationArray();

	public static final String[] GENERAL_MANAGER_LEVEL = PopulateGeneralManagerAuthorizationArray();

	public static final String[] GENERAL_USER_LEVEL = PopulateGeneralUserAuthorizationArray();

	public static String[] PopulateSystemAdministratorAuthorizationArray() {

		List<String> authorizations = new ArrayList<>();

		authorizations.add(Authorizations.SYSTEM_ADMINISTRATOR_AUTHORIZATION_TITLE);

		return authorizations.toArray(new String[authorizations.size()]);
	}

	private static String[] PopulateCentralManagerAuthorizationArray() {

		List<String> authorizations = new ArrayList<>();

		authorizations.add(Authorizations.CENTRAL_MANAGER_AUTHORIZATION_TITLE);

		return authorizations.toArray(new String[authorizations.size()]);
	}

	private static String[] PopulateDepartmentManagerAuthorizationArray() {

		List<String> authorizations = new ArrayList<>(Arrays.asList(PopulateCentralManagerAuthorizationArray()));

		authorizations.add(Authorizations.DEPARTMENT_MANAGER_AUTHORIZATION_TITLE);

		return authorizations.toArray(new String[authorizations.size()]);
	}

	private static String[] PopulateGeneralManagerAuthorizationArray() {

		List<String> authorizations = new ArrayList<>(Arrays.asList(PopulateDepartmentManagerAuthorizationArray()));

		authorizations.add(Authorizations.GENERAL_MANAGER_AUTHORIZATION_TITLE);

		return authorizations.toArray(new String[authorizations.size()]);
	}

	private static String[] PopulateGeneralUserAuthorizationArray() {

		List<String> authorizations = new ArrayList<>(Arrays.asList(PopulateGeneralManagerAuthorizationArray()));

		authorizations.add(Authorizations.GENERAL_USER_AUTHORIZATION_TITLE);

		return authorizations.toArray(new String[authorizations.size()]);
	}

	public static final String SYSTEM_ADMINISTRATOR_AUTHORIZATION_TITLE = "system_administrator";
	public static final String CENTRAL_MANAGER_AUTHORIZATION_TITLE = "central_manager";
	public static final String DEPARTMENT_MANAGER_AUTHORIZATION_TITLE = "department_manager";
	public static final String GENERAL_MANAGER_AUTHORIZATION_TITLE = "general_manager";
	public static final String GENERAL_USER_AUTHORIZATION_TITLE = "general_user";
	public static final String PRISTINE_USER_AUTHORIZATION_TITLE = "pristine_user";

}
