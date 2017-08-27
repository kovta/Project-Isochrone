package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserTaskAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;
import com.kota.stratagem.persistence.util.Constants;

@Stateless(mappedName = "ejb/taskProtocol")
public class TaskProtocolImpl implements TaskProtocol {

	private static final Logger LOGGER = Logger.getLogger(TaskProtocolImpl.class);

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private TaskConverter taskConverter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@EJB
	private LifecycleOverseer overseer;

	@Override
	public TaskRepresentor getTask(Long id) throws AdaptorException {
		try {
			final TaskRepresentor representor = this.taskConverter.toComplete(this.taskService.readComplete(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Task (id: " + id + ") --> " + representor);
			}
			Collections.sort(representor.getAssignedUsers(), new Comparator<AppUserTaskAssignmentRepresentor>() {
				@Override
				public int compare(AppUserTaskAssignmentRepresentor obj_a, AppUserTaskAssignmentRepresentor obj_b) {
					return obj_a.getRecipient().getName().toLowerCase().compareTo(obj_b.getRecipient().getName().toLowerCase());
				}
			});
			final List<Long> identifiers = new ArrayList<>();
			if (!representor.getDependantTasks().isEmpty()) {
				final List<List<TaskRepresentor>> dependantChain = new ArrayList<>();
				final Queue<TaskRepresentor> queue = new LinkedList<TaskRepresentor>();
				queue.add(representor);
				TaskRepresentor current;
				while ((current = queue.poll()) != null) {
					this.traverseDependants(current, queue, dependantChain, identifiers);
				}
				representor.setDependantChain(dependantChain);
			}
			if (!representor.getTaskDependencies().isEmpty()) {
				final List<List<TaskRepresentor>> dependencyChain = new ArrayList<>();
				final Queue<TaskRepresentor> queue = new LinkedList<TaskRepresentor>();
				queue.add(representor);
				TaskRepresentor current;
				while ((current = queue.poll()) != null) {
					this.traverseDependencies(current, queue, dependencyChain, identifiers);
				}
				representor.setDependencyChain(dependencyChain);
			}
			Collections.reverse(representor.getDependantChain());
			for (final List<TaskRepresentor> dependantLevel : representor.getDependantChain()) {
				Collections.sort(dependantLevel, new Comparator<TaskRepresentor>() {
					@Override
					public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
				});
			}
			for (final List<TaskRepresentor> dependencyLevel : representor.getDependantChain()) {
				Collections.sort(dependencyLevel, new Comparator<TaskRepresentor>() {
					@Override
					public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
				});
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	private void traverseDependants(TaskRepresentor node, Queue<TaskRepresentor> queue, List<List<TaskRepresentor>> dependantChain, List<Long> identifiers)
			throws PersistenceServiceException {
		if (!node.getDependantTasks().isEmpty()) {
			final List<TaskRepresentor> nodes = new ArrayList<TaskRepresentor>(node.getDependantTasks());
			for (final TaskRepresentor dependant : node.getDependantTasks()) {
				if (identifiers.contains(dependant.getId())) {
					nodes.remove(dependant);
				} else {
					identifiers.add(dependant.getId());
				}
			}
			if (!nodes.isEmpty()) {
				dependantChain.add(nodes);
			}
			for (int i = 0; i < node.getDependantTasks().size(); i++) {
				final TaskRepresentor dependantNode = this.taskConverter
						.toSimplified(this.taskService.readWithDirectDependencies(node.getDependantTasks().get(i).getId()));
				queue.add(dependantNode);
			}
		}
	}

	private void traverseDependencies(TaskRepresentor node, Queue<TaskRepresentor> queue, List<List<TaskRepresentor>> dependencyChain, List<Long> identifiers)
			throws PersistenceServiceException {
		if (!node.getTaskDependencies().isEmpty()) {
			final List<TaskRepresentor> nodes = new ArrayList<TaskRepresentor>(node.getTaskDependencies());
			for (final TaskRepresentor dependency : node.getTaskDependencies()) {
				if (identifiers.contains(dependency.getId())) {
					nodes.remove(dependency);
				} else {
					identifiers.add(dependency.getId());
				}
			}
			if (!nodes.isEmpty()) {
				dependencyChain.add(nodes);
			}
			for (int i = 0; i < node.getTaskDependencies().size(); i++) {
				final TaskRepresentor dependencyNode = this.taskConverter
						.toSimplified(this.taskService.readWithDirectDependencies(node.getTaskDependencies().get(i).getId()));
				queue.add(dependencyNode);
			}
		}
	}

	@Override
	public List<TaskRepresentor> getCompliantDependencyConfigurations(TaskRepresentor task) throws AdaptorException {
		final List<TaskRepresentor> configurations = new ArrayList<>();
		try {
			if (task.getObjective() != null) {
				configurations.addAll(this.taskConverter.toElementary(this.objectiveService.readWithTasks(task.getObjective().getId()).getTasks()));
			} else if (task.getProject() != null) {
				configurations.addAll(this.taskConverter.toElementary(this.projectService.readWithTasks(task.getProject().getId()).getTasks()));
			} else if (task.getSubmodule() != null) {
				configurations.addAll(this.taskConverter.toElementary(this.submoduleService.readWithTasks(task.getSubmodule().getId()).getTasks()));
			}
			configurations.remove(task);
			for (final List<TaskRepresentor> dependencyLevel : task.getDependencyChain()) {
				for (final TaskRepresentor dependency : dependencyLevel) {
					configurations.remove(dependency);
				}
			}
			for (final List<TaskRepresentor> dependantLevel : task.getDependantChain()) {
				for (final TaskRepresentor dependant : dependantLevel) {
					configurations.remove(dependant);
				}
			}
			Collections.sort(configurations, new Comparator<TaskRepresentor>() {
				@Override
				public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
			});
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Tasks configurable as dependencies for : " + task + " | " + configurations.size() + " configuration(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
		return configurations;
	}

	@Override
	public List<TaskRepresentor> getAllTasks() throws AdaptorException {
		Set<TaskRepresentor> representors = new HashSet<>();
		try {
			representors = this.taskConverter.toSimplified(this.taskService.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Tasks --> " + representors.size() + " item(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
		return new ArrayList<TaskRepresentor>(representors);
	}

	@Override
	public TaskRepresentor saveTask(Long id, String name, String description, int priority, double completion, Date deadline, String operator, Long objective,
			Long project, Long submodule) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(id != null ? "Update Task (id: " + id + ")" : "Create Task (" + name + ")");
			}
			TaskRepresentor origin = null;
			if (id != null) {
				origin = this.taskConverter.toElementary(this.taskService.readElementary(id));
			}
			final TaskRepresentor representor = this.taskConverter.toComplete(((id != null) && this.taskService.exists(id)) ? this.taskService.update(id, name,
					description, priority, completion, deadline, this.appUserService.readElementary(operator), objective, project, submodule)
					: this.taskService.create(name, description, priority, completion, deadline, this.appUserService.readElementary(operator), objective,
							project, submodule));
			if (id != null) {
				this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
			} else {
				this.overseer.created(representor.toTextMessage());
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeTask(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove Task (id: " + id + ")");
			}
			this.overseer.deleted(this.taskConverter.toElementary(this.taskService.readElementary(id)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
					+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName());
			this.taskService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void saveTaskDependencies(Long source, Long[] dependencies) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Save Task dependencies (source: " + source + ", " + dependencies.length + " dependencies)");
			}
			for (final Long dependency : dependencies) {
				this.taskService.createDependency(dependency, source);
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}