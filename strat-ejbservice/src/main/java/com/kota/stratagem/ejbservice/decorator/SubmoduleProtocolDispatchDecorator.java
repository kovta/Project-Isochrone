package com.kota.stratagem.ejbservice.decorator;

import java.util.Date;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.SubmoduleProtocol;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.util.Constants;

@Decorator
public abstract class SubmoduleProtocolDispatchDecorator implements SubmoduleProtocol {

	@EJB
	private SubmoduleService submoduleService;

	@Inject
	@Delegate
	private SubmoduleProtocol protocol;

	@Inject
	private SubmoduleConverter converter;

	@Inject
	private LifecycleOverseer overseer;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Override
	public SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Long project) throws AdaptorException {
		final SubmoduleRepresentor origin = id == null ? null : this.converter.toDispatchable(this.submoduleService.readWithMonitoring(id));
		final SubmoduleRepresentor representor = this.protocol.saveSubmodule(id, name, description, deadline, operator, project);
		if (id != null) {
			this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
		} else {
			this.overseer.created(representor.toTextMessage());
		}
		return representor;
	}

	@Override
	public void removeSubmodule(Long id) throws AdaptorException {
		final String message = this.converter.toDispatchable(this.submoduleService.readWithMonitoring(id)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
				+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		this.protocol.removeSubmodule(id);
		this.overseer.deleted(message);
	}

	@Override
	public void saveSubmoduleDependencies(Long source, Long[] dependencies) throws AdaptorException {
		this.protocol.saveSubmoduleDependencies(source, dependencies);
		for (final Long dependency : dependencies) {
			this.overseer.configured(this.converter.toDispatchable(this.submoduleService.readWithMonitoring(source)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
					+ this.converter.toDispatchable(this.submoduleService.readWithMonitoring(dependency)).toTextMessage());
		}
	}

	@Override
	public void saveSubmoduleDependants(Long source, Long[] dependants) throws AdaptorException {
		this.protocol.saveSubmoduleDependants(source, dependants);
		for (final Long dependency : dependants) {
			this.overseer.configured(this.converter.toDispatchable(this.submoduleService.readWithMonitoring(source)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
					+ this.converter.toDispatchable(this.submoduleService.readWithMonitoring(dependency)).toTextMessage());
		}
	}

	@Override
	public void removeSubmoduleDependency(Long dependency, Long dependant) throws AdaptorException {
		this.protocol.removeSubmoduleDependency(dependency, dependant);
		this.overseer.deconfigured(this.converter.toDispatchable(this.submoduleService.readWithMonitoring(dependant)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
				+ this.converter.toDispatchable(this.submoduleService.readWithMonitoring(dependency)).toTextMessage());
	}

}
