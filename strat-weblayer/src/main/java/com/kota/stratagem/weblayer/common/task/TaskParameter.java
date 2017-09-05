package com.kota.stratagem.weblayer.common.task;

public interface TaskParameter {

	public static final String PARENT_OBJECTIVE = "objectiveId";
	public static final String PARENT_PROJECT = "projectId";
	public static final String PARENT_SUBMODULE = "submoduleId";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String PRIORITY = "priority";
	public static final String COMPLETION = "completion";
	public static final String DEADLINE = "deadline";
	public static final String ADMITTANCE = "admittance";

	public static final String DURATION_TYPE = "durationType";
	public static final String DURATION = "duration";
	public static final String PESSIMISTIC_DURATION = "pessimisticDuration";
	public static final String REALISTIC_DURATION = "realisticDuration";
	public static final String OPTIMISTIC_DURATION = "optimisticDuration";

	public static final String TASK = "taskId";
	public static final String DEPENDENCIES = "dependencies";
	public static final String DEPENDANTS = "dependants";

	public static final String DEPENDENCY = "dependency";
	public static final String DEPENDANT = "dependant";

	public static final String EDIT_FLAG = "edit";

}
