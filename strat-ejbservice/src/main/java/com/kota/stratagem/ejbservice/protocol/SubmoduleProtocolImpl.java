package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.util.Constants;

@Regulated
@Stateless(mappedName = "ejb/submoduleProtocol")
public class SubmoduleProtocolImpl implements SubmoduleProtocol {

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private AppUserService appUserService;

	@Inject
	private SubmoduleConverter submoduleConverter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@EJB
	private LifecycleOverseer overseer;

	@Override
	public SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException {
		final SubmoduleRepresentor representor = this.submoduleConverter.toComplete(this.submoduleService.readComplete(id));
		Collections.sort(representor.getAssignedUsers(), new Comparator<AppUserSubmoduleAssignmentRepresentor>() {
			@Override
			public int compare(AppUserSubmoduleAssignmentRepresentor obj_a, AppUserSubmoduleAssignmentRepresentor obj_b) {
				return obj_a.getRecipient().getName().toLowerCase().compareTo(obj_b.getRecipient().getName().toLowerCase());
			}
		});
		return representor;
	}

	@Override
	public List<SubmoduleRepresentor> getAllSubmodules() throws AdaptorException {
		return new ArrayList<SubmoduleRepresentor>(this.submoduleConverter.toSimplified(this.submoduleService.readAll()));
	}

	@Override
	public SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Long project) throws AdaptorException {
		SubmoduleRepresentor origin = null;
		if (id != null) {
			origin = this.submoduleConverter.toDispatchable(this.submoduleService.readWithMonitoring(id));
		}
		final SubmoduleRepresentor representor = this.submoduleConverter.toComplete(((id != null) && this.submoduleService.exists(id))
				? this.submoduleService.update(id, name, description, deadline, this.appUserService.readElementary(operator))
				: this.submoduleService.create(name, description, deadline, this.appUserService.readElementary(operator), project));
		if (id != null) {
			this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
		} else {
			this.overseer.created(representor.toTextMessage());
		}
		return representor;
	}

	@Override
	public void removeSubmodule(Long id) throws AdaptorException {
		try {
			final String message = this.submoduleConverter.toDispatchable(this.submoduleService.readWithMonitoring(id)).toTextMessage()
					+ Constants.PAYLOAD_SEPARATOR + this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
			this.submoduleService.delete(id);
			this.overseer.deleted(message);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

}
