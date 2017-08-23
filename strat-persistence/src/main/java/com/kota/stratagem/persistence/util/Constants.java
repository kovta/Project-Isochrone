package com.kota.stratagem.persistence.util;

public class Constants {

	// Core
	public static final String APP_USER_DATA_NAME = "AppUser";
	public static final String OBJECTIVE_DATA_NAME = "Objective";
	public static final String PROJECT_DATA_NAME = "Project";
	public static final String SUBMODULE_DATA_NAME = "Submodule";
	public static final String TASK_DATA_NAME = "Task";
	public static final String ASSIGNMENT_DATA_NAME = "Assignment";
	public static final String REPRESENTOR_DATA_NAME = "Representor";

	// Representor
	public static final String OBJECTIVE_REPRESENTOR_DATA_NAME = Constants.OBJECTIVE_DATA_NAME + Constants.REPRESENTOR_DATA_NAME;
	public static final String PROJECT_REPRESENTOR_DATA_NAME = Constants.PROJECT_DATA_NAME + Constants.REPRESENTOR_DATA_NAME;
	public static final String SUBMODULE_REPRESENTOR_DATA_NAME = Constants.SUBMODULE_DATA_NAME + Constants.REPRESENTOR_DATA_NAME;
	public static final String TASK_REPRESENTOR_DATA_NAME = Constants.TASK_DATA_NAME + Constants.REPRESENTOR_DATA_NAME;

	// Assignment
	public static final String APP_USER_OBJECTIVE_ASSIGNMENT_REPRESENTOR_DATA_NAME = Constants.APP_USER_DATA_NAME + Constants.OBJECTIVE_DATA_NAME
			+ Constants.ASSIGNMENT_DATA_NAME + Constants.REPRESENTOR_DATA_NAME;
	public static final String APP_USER_PROJECT_ASSIGNMENT_REPRESENTOR_DATA_NAME = Constants.APP_USER_DATA_NAME + Constants.PROJECT_DATA_NAME
			+ Constants.ASSIGNMENT_DATA_NAME + Constants.REPRESENTOR_DATA_NAME;
	public static final String APP_USER_SUBMODULE_ASSIGNMENT_REPRESENTOR_DATA_NAME = Constants.SUBMODULE_DATA_NAME + Constants.ASSIGNMENT_DATA_NAME
			+ Constants.REPRESENTOR_DATA_NAME;
	public static final String APP_USER_TASK_ASSIGNMENT_REPRESENTOR_DATA_NAME = Constants.APP_USER_DATA_NAME + Constants.TASK_DATA_NAME
			+ Constants.ASSIGNMENT_DATA_NAME + Constants.REPRESENTOR_DATA_NAME;

	// Operations
	public static final String CREATION_SELECTOR = "create";
	public static final String READ_SELECTOR = "read";
	public static final String UPDATE_SELECTOR = "update";
	public static final String DELETION_SELECTOR = "delete";
	public static final String ASSIGNMENT_SELECTOR = "assign";
	public static final String DISSOCIATION_SELECTOR = "dissociation";

	// Separator
	public static final String PAYLOAD_SEPARATOR = " | ";
	public static final String DATA_SEPARATOR = "_";

	// Attribute
	public static final String ID_DATA_NAME = "id";
	public static final String NAME_DATA_NAME = "name";
	public static final String DESCRIPTION_DATA_NAME = "description";
	public static final String PRIORITY_DATA_NAME = "priority";
	public static final String STATUS_DATA_NAME = "status";
	public static final String DEADLINE_DATA_NAME = "deadline";
	public static final String CONFIDENTIALITY_DATA_NAME = "confidential";
	public static final String COMPLETION_DATA_NAME = "completion";
	public static final String CREATOR_DATA_NAME = "creator";
	public static final String CREATOR_ID_DATA_NAME = Constants.CREATOR_DATA_NAME + Constants.DATA_SEPARATOR + Constants.ID_DATA_NAME;
	public static final String CREATION_DATE_DATA_NAME = "creationDate";
	public static final String MODIFIER_DATA_NAME = "modifier";
	public static final String MODIFIER_ID_DATA_NAME = Constants.MODIFIER_DATA_NAME + Constants.DATA_SEPARATOR + Constants.ID_DATA_NAME;
	public static final String MODIFICATION_DATE_DATA_NAME = "modificationDate";

	public static final String OBJECTIVE_ATTRIBUTE_DATA_NAME = "objective";
	public static final String PROJECT_ATTRIBUTE_DATA_NAME = "project";
	public static final String SUBMODULE_ATTRIBUTE_DATA_NAME = "submodule";
	public static final String TASK_ATTRIBUTE_DATA_NAME = "task";

	public static final String ENTRUSTOR_ATTRIBUTE_DATA_NAME = "entrustor";
	public static final String RECIPIENT_ATTRIBUTE_DATA_NAME = "recipient";

	public static final String OBJECTIVE_ID_ATTRIBUTE_DATA_NAME = Constants.OBJECTIVE_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR + Constants.ID_DATA_NAME;
	public static final String PROJECT_ID_ATTRIBUTE_DATA_NAME = Constants.PROJECT_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR + Constants.ID_DATA_NAME;
	public static final String SUBMODULE_ID_ATTRIBUTE_DATA_NAME = Constants.SUBMODULE_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR + Constants.ID_DATA_NAME;

	public static final String OBJECTIVE_NAME_ATTRIBUTE_DATA_NAME = Constants.OBJECTIVE_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR
			+ Constants.NAME_DATA_NAME;
	public static final String PROJECT_NAME_ATTRIBUTE_DATA_NAME = Constants.PROJECT_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR + Constants.NAME_DATA_NAME;
	public static final String SUBMODULE_NAME_ATTRIBUTE_DATA_NAME = Constants.SUBMODULE_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR
			+ Constants.NAME_DATA_NAME;
	public static final String TASK_NAME_ATTRIBUTE_DATA_NAME = Constants.TASK_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR + Constants.NAME_DATA_NAME;

	public static final String ENTRUSTOR_ID_DATA_NAME = Constants.ENTRUSTOR_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR + Constants.ID_DATA_NAME;
	public static final String RECIPIENT_ID_DATA_NAME = Constants.RECIPIENT_ATTRIBUTE_DATA_NAME + Constants.DATA_SEPARATOR + Constants.ID_DATA_NAME;

}
