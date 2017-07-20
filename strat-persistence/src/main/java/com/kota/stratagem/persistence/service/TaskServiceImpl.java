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
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
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

	@Override
	public Task create(String name, String description, int priority, double completion, Date deadline, AppUser creator, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers, Set<Impediment> impediments, Set<Task> dependantTasks, Set<Task> taskDependencies, Long objective, Long project)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Task (name: " + name + ", description: " + description + ", completion: " + completion + ")");
		}
		try {
			final Task task = new Task(name, description, priority, completion, deadline, new Date(), new Date(), assignedTeams, assignedUsers, impediments,
					dependantTasks, taskDependencies);
			Objective parentObjective = null;
			Project parentProject = null;
			if (objective != null) {
				parentObjective = this.objectiveService.readElementary(objective);
				task.setObjective(parentObjective);
			} else if (project != null) {
				parentProject = this.projectService.readElementary(project);
				task.setProject(parentProject);
			}
			AppUser operatorTemp;
			if (objective != null) {
				if (parentObjective.getCreator().getId() == creator.getId()) {
					operatorTemp = parentObjective.getCreator();
				} else {
					operatorTemp = this.appUserService.read(creator.getId());
				}
			} else if (project != null) {
				if (parentProject.getCreator().getId() == creator.getId()) {
					operatorTemp = parentProject.getCreator();
				} else {
					operatorTemp = this.appUserService.read(creator.getId());
				}
			} else {
				operatorTemp = this.appUserService.read(creator.getId());
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
	public Task read(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Task by id (" + id + ")");
		}
		Task result = null;
		try {
			result = this.entityManager.createNamedQuery(TaskQuery.GET_BY_ID, Task.class).setParameter(TaskParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Task by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
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
	public Task update(Long id, String name, String description, int priority, double completion, Date deadline, AppUser modifier, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers, Set<Impediment> impediments, Set<Task> dependantTasks, Set<Task> taskDependencies, Long objective, Long project)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Task (id: " + id + ", name: " + name + ", description: " + description + ", completion: " + completion + ")");
		}
		try {
			final Task task = this.read(id);
			final AppUser operator = this.appUserService.read(modifier.getId());
			task.setName(name);
			task.setDescription(description);
			task.setPriority(priority);
			task.setCompletion(completion);
			task.setDeadline(deadline);
			if (!(task.getModifier().equals(operator))) {
				if (!(task.getCreator().equals(task.getModifier()))) {
					task.setModifier(operator);
				} else if (task.getCreator().equals(operator)) {
					task.setModifier(task.getCreator());
				}
			}
			task.setModificationDate(new Date());
			task.setAssignedTeams(assignedTeams != null ? assignedTeams : new HashSet<Team>());
			task.setAssignedUsers(assignedUsers != null ? assignedUsers : new HashSet<AppUser>());
			task.setImpediments(impediments != null ? impediments : new HashSet<Impediment>());
			task.setDependantTasks(dependantTasks != null ? dependantTasks : new HashSet<Task>());
			task.setTaskDependencies(taskDependencies != null ? taskDependencies : new HashSet<Task>());
			if (objective != null) {
				task.setObjective(this.objectiveService.readWithTasks(objective));
			} else if (project != null) {
				task.setProject(this.projectService.readWithTasks(project));
			}
			return this.entityManager.merge(task);
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when merging Task! " + e.getLocalizedMessage(), e);
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
			throw new PersistenceServiceException("Unknown error when removing Task by id (" + id + ")! " + e.getLocalizedMessage(), e);
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