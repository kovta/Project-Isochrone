package com.kota.stratagem.persistence.query;

public class TaskQuery {

	public static final String COUNT_BY_ID = "Tasks.countById";
	public static final String GET_BY_ID = "Tasks.getById";
	public static final String GET_BY_ID_WITH_ASSIGNMENTS = "Tasks.getByIdWithAssignments";
	public static final String GET_BY_ID_WITH_DEPENDENCIES = "Tasks.getByIdWithDependencies";
	public static final String GET_BY_ID_WITH_DEPENDANTS = "Tasks.getByIdWithDependants";
	public static final String GET_BY_ID_WITH_DIRECT_DEPENDENCIES = "Tasks.getByIdWithDirectDependencies";
	public static final String GET_BY_ID_COMPLETE = "Tasks.getByIdComplete";
	public static final String GET_ALL_TASKS = "Tasks.getAll";
	public static final String REMOVE_BY_ID = "Tasks.removeById";

}