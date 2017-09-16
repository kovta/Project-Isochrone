package com.kota.stratagem.ejbservice.decorator;

import java.util.Date;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ProjectProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.util.Constants;

@Decorator
public abstract class ProjectProtocolDispatchDecorator implements ProjectProtocol {

	@EJB
	private ProjectService projectService;

	@Inject
	@Delegate
	private ProjectProtocol protocol;

	@Inject
	private ProjectConverter converter;

	@Inject
	private LifecycleOverseer overseer;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Override
	public ProjectRepresentor saveProject(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			String operator, Long objective) throws AdaptorException {
		final ProjectRepresentor origin = id == null ? null : this.converter.toDispatchable(this.projectService.readWithMonitoring(id));
		final ProjectRepresentor representor = this.protocol.saveProject(id, name, description, status, deadline, confidential, operator, objective);
		if (id != null) {
			this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
		} else {
			this.overseer.created(representor.toTextMessage());
		}
		return representor;
	}

	@Override
	public void removeProject(Long id) throws AdaptorException {
		final String message = this.converter.toDispatchable(this.projectService.readWithMonitoring(id)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
				+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		this.protocol.removeProject(id);
		this.overseer.deleted(message);
	}

}
