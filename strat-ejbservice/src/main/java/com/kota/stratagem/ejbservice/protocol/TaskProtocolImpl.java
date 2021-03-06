package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.TaskOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.security.context.SessionContextAccessor;
import com.kota.stratagem.security.domain.RestrictionLevel;
import com.kota.stratagem.security.interceptor.Authorized;

@Regulated
@Stateless(mappedName = EJBServiceConfiguration.TASK_PROTOCOL_SIGNATURE)
public class TaskProtocolImpl implements TaskProtocol {

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private TaskService taskService;

	@EJB
	private AppUserService appUserService;

	@Inject
	private TaskConverter taskConverter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Inject
	@TaskOriented
	private DTOExtensionManager extensionManager;

	@Override
	public TaskRepresentor getTask(Long id) throws AdaptorException {
		return (TaskRepresentor) this.extensionManager.prepare(this.taskConverter.toComplete(this.taskService.readComplete(id)));
	}

	@Override
	public List<TaskRepresentor> getCompliantDependencyConfigurations(TaskRepresentor task) throws AdaptorException {
		final List<TaskRepresentor> configurations = new ArrayList<>();
		if(task.getObjective() != null) {
			configurations.addAll(this.taskConverter.toElementary(this.objectiveService.readWithTasks(task.getObjective().getId()).getTasks()));
		} else if(task.getProject() != null) {
			configurations.addAll(this.taskConverter.toElementary(this.projectService.readWithTasks(task.getProject().getId()).getTasks()));
		} else if(task.getSubmodule() != null) {
			configurations.addAll(this.taskConverter.toElementary(this.submoduleService.readWithTasks(task.getSubmodule().getId()).getTasks()));
		}
		configurations.remove(task);
		for(final List<TaskRepresentor> dependencyLevel : task.getDependencyChain()) {
			for(final TaskRepresentor dependency : dependencyLevel) {
				configurations.remove(dependency);
			}
		}
		for(final List<TaskRepresentor> dependantLevel : task.getDependantChain()) {
			for(final TaskRepresentor dependant : dependantLevel) {
				configurations.remove(dependant);
			}
		}
		return (List<TaskRepresentor>) this.extensionManager.prepareBatch(configurations);
	}

	@Override
	public List<TaskRepresentor> getAllTasks() throws AdaptorException {
		return new ArrayList<TaskRepresentor>(this.taskConverter.toSimplified(this.taskService.readAll()));
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public TaskRepresentor saveTask(Long id, String name, String description, int priority, double completion, Date deadline, Boolean admittance, String operator, Long objective, Long project,
			Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic) throws AdaptorException {
		return (TaskRepresentor) this.extensionManager.prepare(this.taskConverter.toComplete(((id != null) && this.taskService.exists(id))
				? this.taskService.update(id, name, description, priority, completion, deadline, admittance, operator, objective, project, submodule, duration, pessimistic, realistic, optimistic)
				: this.taskService.create(name, description, priority, completion, deadline, admittance, operator, objective, project, submodule, duration, pessimistic, realistic, optimistic)));
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public TaskRepresentor moveTask(Long id, Long submodule) throws AdaptorException {
		return (TaskRepresentor) this.extensionManager
				.prepare(this.taskConverter.toComplete(this.taskService.move(id, submodule, this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName())));
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void removeTask(Long id) throws AdaptorException {
		try {
			this.taskService.delete(id);
		} catch(final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void saveTaskDependencies(Long source, Long[] dependencies) throws AdaptorException {
		for(final Long dependency : dependencies) {
			this.taskService.createDependency(dependency, source, this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void saveTaskDependants(Long source, Long[] dependants) throws AdaptorException {
		for(final Long dependant : dependants) {
			this.taskService.createDependency(source, dependant, this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void removeTaskDependency(Long dependency, Long dependant) throws AdaptorException {
		this.taskService.deleteDependency(dependency, dependant, this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
	}

}