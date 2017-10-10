package com.kota.stratagem.ejbservice.decorator;

import java.util.Date;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.util.Constants;
import com.kota.stratagem.security.context.SessionContextAccessor;

@Decorator
public abstract class ObjectiveProtocolDispatchDecorator implements ObjectiveProtocol {

	@EJB
	private ObjectiveService objectiveService;

	@Inject
	@Delegate
	private ObjectiveProtocol protocol;

	@Inject
	private ObjectiveConverter converter;

	@Inject
	private LifecycleOverseer overseer;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Override
	public ObjectiveRepresentor saveObjective(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline,
			Boolean confidentiality, String operator) throws AdaptorException {
		final ObjectiveRepresentor origin = id == null ? null : this.converter.toDispatchable(this.objectiveService.readWithMonitoring(id));
		final ObjectiveRepresentor representor = this.protocol.saveObjective(id, name, description, priority, status, deadline, confidentiality, operator);
		if (id != null) {
			this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
		} else {
			this.overseer.created(representor.toTextMessage());
		}
		return representor;
	}

	@Override
	public void removeObjective(Long id) throws AdaptorException {
		final String message = this.converter.toDispatchable(this.objectiveService.readWithMonitoring(id)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
				+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		this.protocol.removeObjective(id);
		this.overseer.deleted(message);
	}

}
