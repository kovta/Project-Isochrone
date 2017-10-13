package com.kota.stratagem.persistence.util;

public class Constants {

	// Core
	public static final String APP_USER_DATA_LABEL = "AppUser";
	public static final String TEAM_DATA_LABEL = "Team";
	public static final String OBJECTIVE_DATA_LABEL = "Objective";
	public static final String PROJECT_DATA_LABEL = "Project";
	public static final String SUBMODULE_DATA_LABEL = "Submodule";
	public static final String TASK_DATA_LABEL = "Task";
	public static final String ASSIGNMENT_DATA_LABEL = "Assignment";
	public static final String IMPEDIMENT_DATA_LABEL = "Impediment";
	public static final String REMEDY_DATA_LABEL = "Remedy";
	public static final String REPRESENTOR_DATA_LABEL = "Representor";

	// Representor
	public static final String OBJECTIVE_REPRESENTOR_DATA_LABEL = Constants.OBJECTIVE_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;
	public static final String PROJECT_REPRESENTOR_DATA_LABEL = Constants.PROJECT_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;
	public static final String SUBMODULE_REPRESENTOR_DATA_LABEL = Constants.SUBMODULE_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;
	public static final String TASK_REPRESENTOR_DATA_LABEL = Constants.TASK_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;

	// Assignment
	public static final String APP_USER_OBJECTIVE_ASSIGNMENT_REPRESENTOR_DATA_LABEL = Constants.APP_USER_DATA_LABEL + Constants.OBJECTIVE_DATA_LABEL
			+ Constants.ASSIGNMENT_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;
	public static final String APP_USER_PROJECT_ASSIGNMENT_REPRESENTOR_DATA_LABEL = Constants.APP_USER_DATA_LABEL + Constants.PROJECT_DATA_LABEL
			+ Constants.ASSIGNMENT_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;
	public static final String APP_USER_SUBMODULE_ASSIGNMENT_REPRESENTOR_DATA_LABEL = Constants.APP_USER_DATA_LABEL + Constants.SUBMODULE_DATA_LABEL 
			+ Constants.ASSIGNMENT_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;
	public static final String APP_USER_TASK_ASSIGNMENT_REPRESENTOR_DATA_LABEL = Constants.APP_USER_DATA_LABEL + Constants.TASK_DATA_LABEL
			+ Constants.ASSIGNMENT_DATA_LABEL + Constants.REPRESENTOR_DATA_LABEL;

	// Operations
	public static final String CREATION_SELECTOR = "create";
	public static final String READ_SELECTOR = "read";
	public static final String UPDATE_SELECTOR = "update";
	public static final String DELETION_SELECTOR = "delete";
	public static final String ASSIGNMENT_SELECTOR = "assign";
	public static final String DISSOCIATION_SELECTOR = "dissociation";
	public static final String DEPENDENCY_CONFIGURATION_SELECTOR = "dependency configuration";
	public static final String DEPENDENCY_DECONFIGURATION_SELECTOR = "dependency deconfiguration";

	// Separator
	public static final String PAYLOAD_SEPARATOR = " | ";
	public static final String DATA_SEPARATOR = "_";
	public static final String CONCERN_ASSOCIATOR = "/";
	public static final String NULL_ATTRIBUTE = "null";

	// Attribute
	public static final String ID_DATA_LABEL = "id";
	public static final String NAME_DATA_LABEL = "name";
	public static final String DESCRIPTION_DATA_LABEL = "description";
	public static final String PRIORITY_DATA_LABEL = "priority";
	public static final String STATUS_DATA_LABEL = "status";
	public static final String DEADLINE_DATA_LABEL = "deadline";
	public static final String CONFIDENTIALITY_DATA_LABEL = "confidential";
	public static final String COMPLETION_DATA_LABEL = "completion";
	public static final String ADMISSION_DATA_LABEL = "admission";
	public static final String CREATOR_DATA_LABEL = "creator";
	public static final String CREATOR_ID_DATA_LABEL = Constants.CREATOR_DATA_LABEL + Constants.DATA_SEPARATOR + Constants.ID_DATA_LABEL;
	public static final String CREATION_DATE_DATA_LABEL = "creationDate";
	public static final String MODIFIER_DATA_LABEL = "modifier";
	public static final String MODIFIER_ID_DATA_LABEL = Constants.MODIFIER_DATA_LABEL + Constants.DATA_SEPARATOR + Constants.ID_DATA_LABEL;
	public static final String MODIFICATION_DATE_DATA_LABEL = "modificationDate";

	public static final String APP_USER_ATTRIBUTE_DATA_LABEL = "appUser";
	public static final String TEAM_ATTRIBUTE_DATA_LABEL = "team";
	public static final String OBJECTIVE_ATTRIBUTE_DATA_LABEL = "objective";
	public static final String PROJECT_ATTRIBUTE_DATA_LABEL = "project";
	public static final String SUBMODULE_ATTRIBUTE_DATA_LABEL = "submodule";
	public static final String TASK_ATTRIBUTE_DATA_LABEL = "task";
	public static final String IMPEDIMENT_ATTRIBUTE_DATA_LABEL = "impediment";
	public static final String REMEDY_ATTRIBUTE_DATA_LABEL = "remedy";
	public static final String NOTIFICATION_ATTRIBUTE_DATA_LABEL = "notification";

	public static final String ENTRUSTOR_ATTRIBUTE_DATA_LABEL = "entrustor";
	public static final String RECIPIENT_ATTRIBUTE_DATA_LABEL = "recipient";

	public static final String OBJECTIVE_ID_ATTRIBUTE_DATA_LABEL = Constants.OBJECTIVE_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR
			+ Constants.ID_DATA_LABEL;
	public static final String PROJECT_ID_ATTRIBUTE_DATA_LABEL = Constants.PROJECT_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR + Constants.ID_DATA_LABEL;
	public static final String SUBMODULE_ID_ATTRIBUTE_DATA_LABEL = Constants.SUBMODULE_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR
			+ Constants.ID_DATA_LABEL;

	public static final String OBJECTIVE_NAME_ATTRIBUTE_DATA_LABEL = Constants.OBJECTIVE_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR
			+ Constants.NAME_DATA_LABEL;
	public static final String PROJECT_NAME_ATTRIBUTE_DATA_LABEL = Constants.PROJECT_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR
			+ Constants.NAME_DATA_LABEL;
	public static final String SUBMODULE_NAME_ATTRIBUTE_DATA_LABEL = Constants.SUBMODULE_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR
			+ Constants.NAME_DATA_LABEL;
	public static final String TASK_NAME_ATTRIBUTE_DATA_LABEL = Constants.TASK_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR + Constants.NAME_DATA_LABEL;

	public static final String ENTRUSTOR_ID_DATA_LABEL = Constants.ENTRUSTOR_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR + Constants.ID_DATA_LABEL;
	public static final String RECIPIENT_ID_DATA_LABEL = Constants.RECIPIENT_ATTRIBUTE_DATA_LABEL + Constants.DATA_SEPARATOR + Constants.ID_DATA_LABEL;

}
