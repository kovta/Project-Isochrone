package com.kota.stratagem.persistence.service;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class IntegratedDependencyContainer {

	@Inject
	protected EntityManager entityManager;

	@EJB
	protected AppUserService appUserService;

	@EJB
	protected ObjectiveService objectiveService;

	@EJB
	protected ProjectService projectService;

	@EJB
	protected SubmoduleService submoduleService;

	@EJB
	protected TaskService taskService;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public AppUserService getAppUserService() {
		return this.appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public ObjectiveService getObjectiveService() {
		return this.objectiveService;
	}

	public void setObjectiveService(ObjectiveService objectiveService) {
		this.objectiveService = objectiveService;
	}

	public ProjectService getProjectService() {
		return this.projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public SubmoduleService getSubmoduleService() {
		return this.submoduleService;
	}

	public void setSubmoduleService(SubmoduleService submoduleService) {
		this.submoduleService = submoduleService;
	}

	public TaskService getTaskService() {
		return this.taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

}
