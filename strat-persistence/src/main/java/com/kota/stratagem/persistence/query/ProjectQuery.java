package com.kota.stratagem.persistence.query;

public class ProjectQuery {

	public static final String COUNT_BY_ID = "Projects.countById";
	public static final String GET_BY_ID = "Projects.getById";
	public static final String GET_BY_ID_WITH_MONITORING = "Projects.getByIdWithMonitoring";
	public static final String GET_BY_ID_WITH_ASSIGNMENTS = "Projects.getByIdWithAssignments";
	public static final String GET_BY_ID_WITH_TASKS = "Projects.getByIdWithTasks";
	public static final String GET_BY_ID_WITH_SUBMODULES = "Projects.getByIdWithSubmodules";
	public static final String GET_BY_ID_WITH_SUBMODULES_AND_TASKS = "Projects.getByIdWithSubmodulesAndTasks";
	public static final String GET_BY_ID_COMPLETE = "Projects.getByIdComplete";
	public static final String GET_ALL_BY_STATUS = "Projects.getAllByStatus";
	public static final String GET_ALL_PROJECTS = "Projects.getAll";
	public static final String REMOVE_BY_ID = "Projects.removeById";

}