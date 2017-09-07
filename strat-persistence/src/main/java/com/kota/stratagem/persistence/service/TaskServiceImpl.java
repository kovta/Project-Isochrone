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

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.TaskEstimation;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.TaskParameter;
import com.kota.stratagem.persistence.query.TaskEstimationQuery;
import com.kota.stratagem.persistence.query.TaskQuery;

@Contained
@Stateless(mappedName = "ejb/taskService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TaskServiceImpl implements TaskService {

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

	@Override
	public Task create(String name, String description, int priority, double completion, Date deadline, Boolean admittance, AppUser creator, Long objective,
			Long project, Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic) {
		final Task task = new Task(name, description, priority, completion, deadline, duration, admittance, new Date(), new Date());
		if ((pessimistic != null) && (realistic != null) && (optimistic != null)) {
			task.setEstimation(new TaskEstimation(pessimistic, realistic, optimistic, task));
		}
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
	}

	@Override
	public Task readElementary(Long id) {
		return this.entityManager.createNamedQuery(TaskQuery.GET_BY_ID, Task.class).setParameter(TaskParameter.ID, id).getSingleResult();
	}

	@Override
	public Task readWithAssignments(Long id) {
		return this.entityManager.createNamedQuery(TaskQuery.GET_BY_ID_WITH_ASSIGNMENTS, Task.class).setParameter(TaskParameter.ID, id).getSingleResult();
	}

	@Override
	public Task readWithDependencies(Long id) {
		return this.entityManager.createNamedQuery(TaskQuery.GET_BY_ID_WITH_DEPENDENCIES, Task.class).setParameter(TaskParameter.ID, id).getSingleResult();
	}

	@Override
	public Task readWithDependants(Long id) {
		return this.entityManager.createNamedQuery(TaskQuery.GET_BY_ID_WITH_DEPENDANTS, Task.class).setParameter(TaskParameter.ID, id).getSingleResult();
	}

	@Override
	public Task readWithDirectDependencies(Long id) {
		return this.entityManager.createNamedQuery(TaskQuery.GET_BY_ID_WITH_DIRECT_DEPENDENCIES, Task.class).setParameter(TaskParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Task readComplete(Long id) {
		return this.entityManager.createNamedQuery(TaskQuery.GET_BY_ID_COMPLETE, Task.class).setParameter(TaskParameter.ID, id).getSingleResult();
	}

	@Override
	public Set<Task> readAll() {
		return new HashSet<Task>(this.entityManager.createNamedQuery(TaskQuery.GET_ALL_TASKS, Task.class).getResultList());
	}

	@Override
	public Task update(Long id, String name, String description, int priority, double completion, Date deadline, Boolean admittance, AppUser modifier,
			Long objective, Long project, Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic) {
		final Task task = this.readComplete(id);
		final AppUser operator = this.appUserService.readElementary(modifier.getId());
		task.setName(name);
		task.setDescription(description);
		task.setPriority(priority);
		task.setCompletion(completion);
		task.setDeadline(deadline);
		task.setAdmittance(admittance);
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
		if ((pessimistic != null) && (realistic != null) && (optimistic != null)) {
			task.setEstimation(new TaskEstimation(pessimistic, realistic, optimistic, task));
			task.setDuration(null);
		} else if (duration != null) {
			task.setDuration(duration);
			if (task.getEstimation() != null) {
				this.entityManager.createNamedQuery(TaskEstimationQuery.REMOVE_BY_TASK_ID).setParameter(TaskParameter.ID, id).executeUpdate();
			}
		}
		return this.entityManager.merge(task);
	}

	@Override
	public void delete(Long id) {
		this.entityManager.createNamedQuery(TaskEstimationQuery.REMOVE_BY_TASK_ID).setParameter(TaskParameter.ID, id).executeUpdate();
		this.entityManager.createNamedQuery(TaskQuery.REMOVE_BY_ID).setParameter(TaskParameter.ID, id).executeUpdate();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) {
		return this.entityManager.createNamedQuery(TaskQuery.COUNT_BY_ID, Long.class).setParameter(TaskParameter.ID, id).getSingleResult() == 1;
	}

	@Override
	public void createDependency(Long dependency, Long dependant, Long operator) {
		final Task maintainer = this.readWithDependencies(dependant);
		final Task satiator = this.readWithDependencies(dependency);
		final Set<Task> dependencies = this.manageOperators(satiator, maintainer, operator);
		dependencies.add(this.readElementary(dependency));
		maintainer.setTaskDependencies(dependencies);
		this.entityManager.merge(maintainer);
	}

	@Override
	public void deleteDependency(Long dependency, Long dependant, Long operator) {
		final Task maintainer = this.readWithDependencies(dependant);
		final Task satiator = this.readWithDependencies(dependency);
		final Set<Task> dependencies = this.manageOperators(satiator, maintainer, operator);
		dependencies.remove(this.readElementary(dependency));
		maintainer.setTaskDependencies(dependencies);
		this.entityManager.merge(maintainer);
	}

	private Set<Task> manageOperators(Task satiator, Task maintainer, Long operator) {
		if (maintainer.getCreator().getId() == operator) {
			maintainer.setModifier(maintainer.getCreator());
		} else if (maintainer.getModifier().getId() != operator) {
			maintainer.setModifier(this.appUserService.readElementary(operator));
		}
		maintainer.setModificationDate(new Date());
		if (satiator.getCreator().getId() == operator) {
			satiator.setModifier(satiator.getCreator());
		} else if (satiator.getModifier().getId() != operator) {
			satiator.setModifier(this.appUserService.readElementary(operator));
		}
		this.entityManager.merge(satiator);
		satiator.setModificationDate(new Date());
		return maintainer.getTaskDependencies();
	}

}