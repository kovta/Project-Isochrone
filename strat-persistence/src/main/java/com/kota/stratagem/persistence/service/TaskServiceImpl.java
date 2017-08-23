package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.TaskParameter;
import com.kota.stratagem.persistence.query.TaskQuery;

@Stateless(mappedName = "ejb/taskService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TaskServiceImpl implements TaskService {

	private static final Logger LOGGER = Logger.getLogger(TaskServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleService;

	private Task retrieveSingleRecord(Long id, String query, String message) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}
		Task result = null;
		try {
			result = this.entityManager.createNamedQuery(query, Task.class).setParameter(TaskParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error while fetching Task by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Task create(String name, String description, int priority, double completion, Date deadline, AppUser creator, Long objective, Long project,
			Long submodule) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Task (name: " + name + ", description: " + description + ", completion: " + completion + ")");
		}
		try {
			final Task task = new Task(name, description, priority, completion, deadline, new Date(), new Date());
			Objective parentObjective = null;
			Project parentProject = null;
			Submodule parentSubmodule = null;
			AppUser operatorTemp = null;
			if (objective != null) {
				parentObjective = this.objectiveService.readElementary(objective);
				task.setObjective(parentObjective);
				if (parentObjective.getCreator().getId() == creator.getId()) {
					operatorTemp = parentObjective.getCreator();
				} else {
					operatorTemp = this.appUserService.readElementary(creator.getId());
				}
			} else if (project != null) {
				parentProject = this.projectService.readElementary(project);
				task.setProject(parentProject);
				if (parentProject.getCreator().getId() == creator.getId()) {
					operatorTemp = parentProject.getCreator();
				} else {
					operatorTemp = this.appUserService.readElementary(creator.getId());
				}
			} else if (submodule != null) {
				parentSubmodule = this.submoduleService.readElementary(submodule);
				task.setSubmodule(parentSubmodule);
				if (parentSubmodule.getCreator().getId() == creator.getId()) {
					operatorTemp = parentSubmodule.getCreator();
				} else {
					operatorTemp = this.appUserService.readElementary(creator.getId());
				}
			} else {
				operatorTemp = this.appUserService.readElementary(creator.getId());
			}
			final AppUser operator = operatorTemp;
			task.setCreator(operator);
			task.setModifier(operator);
			this.entityManager.merge(task);
			this.entityManager.flush();
			return task;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Task (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Task readElementary(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, TaskQuery.GET_BY_ID, "Get Task by id (" + id + ")");
	}

	@Override
	public Task readWithAssignments(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, TaskQuery.GET_BY_ID_WITH_ASSIGNMENTS, "Get Task with Assignments by id (" + id + ")");
	}

	@Override
	public Task readComplete(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, TaskQuery.GET_BY_ID_COMPLETE, "Get Task with all attributes by id (" + id + ")");
	}

	@Override
	public Set<Task> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Fetching all tasks");
		}
		Set<Task> result = null;
		try {
			result = new HashSet<Task>(this.entityManager.createNamedQuery(TaskQuery.GET_ALL_TASKS, Task.class).getResultList());
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error occured while fetching tasks" + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Task update(Long id, String name, String description, int priority, double completion, Date deadline, AppUser modifier, Long objective, Long project,
			Long submodule) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Task (id: " + id + ", name: " + name + ", description: " + description + ", completion: " + completion + ")");
		}
		try {
			final Task task = this.readComplete(id);
			final AppUser operator = this.appUserService.readElementary(modifier.getId());
			task.setName(name);
			task.setDescription(description);
			task.setPriority(priority);
			task.setCompletion(completion);
			task.setDeadline(deadline);
			if (task.getCreator().getId() == operator.getId()) {
				task.setModifier(task.getCreator());
			} else if (task.getModifier().getId() != operator.getId()) {
				task.setModifier(operator);
			}
			task.setModificationDate(new Date());
			if (objective != null) {
				task.setObjective(this.objectiveService.readWithTasks(objective));
			} else if (project != null) {
				task.setProject(this.projectService.readWithTasks(project));
			} else if (submodule != null) {
				task.setSubmodule(this.submoduleService.readWithTasks(submodule));
			}
			return this.entityManager.merge(task);
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error while merging Task! " + e.getLocalizedMessage(), e);
		}
	}

	// To be expanded
	@Override
	public void delete(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove Task by id (" + id + ")");
		}
		try {
			this.entityManager.createNamedQuery(TaskQuery.REMOVE_BY_ID).setParameter(TaskParameter.ID, id).executeUpdate();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error while removing Task by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Task by id (" + id + ")");
		}
		try {
			return this.entityManager.createNamedQuery(TaskQuery.COUNT_BY_ID, Long.class).setParameter(TaskParameter.ID, id).getSingleResult() == 1;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during counting Tasks by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}