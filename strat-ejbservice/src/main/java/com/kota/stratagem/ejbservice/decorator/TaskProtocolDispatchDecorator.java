package com.kota.stratagem.ejbservice.decorator;

import java.util.Date;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.TaskProtocol;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.util.Constants;

@Decorator
public abstract class TaskProtocolDispatchDecorator implements TaskProtocol {

	@EJB
	private TaskService taskService;

	@Inject
	@Delegate
	private TaskProtocol protocol;

	@Inject
	private TaskConverter converter;

	@Inject
	private LifecycleOverseer overseer;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Override
	public TaskRepresentor saveTask(Long id, String name, String description, int priority, double completion, Date deadline, Boolean admittance,
			String operator, Long objective, Long project, Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic)
			throws AdaptorException {
		final TaskRepresentor origin = id == null ? null : this.converter.toDispatchable(this.taskService.readWithMonitoring(id));
		final TaskRepresentor representor = this.protocol.saveTask(id, name, description, priority, completion, deadline, admittance, operator, objective,
				project, submodule, duration, pessimistic, realistic, optimistic);
		if (id != null) {
			this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
		} else {
			this.overseer.created(representor.toTextMessage());
		}
		return representor;
	}

	@Override
	public void removeTask(Long id) throws AdaptorException {
		final String message = this.converter.toDispatchable(this.taskService.readWithMonitoring(id)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
				+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		this.protocol.removeTask(id);
		this.overseer.deleted(message);
	}

	@Override
	public void saveTaskDependencies(Long source, Long[] dependencies) throws AdaptorException {
		this.protocol.saveTaskDependencies(source, dependencies);
		for (final Long dependency : dependencies) {
			this.overseer.configured(this.converter.toDispatchable(this.taskService.readWithMonitoring(source)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
					+ this.converter.toDispatchable(this.taskService.readWithMonitoring(dependency)).toTextMessage());
		}
	}

	@Override
	public void saveTaskDependants(Long source, Long[] dependants) throws AdaptorException {
		this.protocol.saveTaskDependants(source, dependants);
		for (final Long dependant : dependants) {
			this.overseer.configured(this.converter.toDispatchable(this.taskService.readWithMonitoring(dependant)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
					+ this.converter.toDispatchable(this.taskService.readWithMonitoring(source)).toTextMessage());
		}
	}

	@Override
	public void removeTaskDependency(Long dependency, Long dependant) throws AdaptorException {
		this.protocol.removeTaskDependency(dependency, dependant);
		this.overseer.deconfigured(this.converter.toDispatchable(this.taskService.readWithMonitoring(dependant)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
				+ this.converter.toDispatchable(this.taskService.readWithMonitoring(dependency)).toTextMessage());
	}

}
