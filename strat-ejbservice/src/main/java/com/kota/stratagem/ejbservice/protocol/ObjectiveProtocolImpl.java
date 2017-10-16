package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.ObjectiveOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.ejbserviceclient.protocol.ObjectiveProtocolRemote;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.security.domain.RestrictionLevel;
import com.kota.stratagem.security.interceptor.Authorized;

@Regulated
@Stateless(mappedName = EJBServiceConfiguration.OBJECTIVE_PROTOCOL_SIGNATURE)
public class ObjectiveProtocolImpl implements ObjectiveProtocol, ObjectiveProtocolRemote {

	@EJB
	private ObjectiveService objectiveService;

	@Inject
	private ObjectiveConverter converter;

	@Inject
	@ObjectiveOriented
	private DTOExtensionManager extensionManager;

	@Override
	public ObjectiveRepresentor getObjective(Long id) throws ServiceException {
		return (ObjectiveRepresentor) this.extensionManager.prepare(this.converter.toComplete(this.objectiveService.readComplete(id)));
	}

	@Override
	public List<ObjectiveRepresentor> getAllObjectives() throws AdaptorException {
		return (List<ObjectiveRepresentor>) this.extensionManager.prepareBatch(new ArrayList<ObjectiveRepresentor>(this.converter.toSimplified(this.objectiveService.readAll())));
	}

	@Override
	@Authorized(RestrictionLevel.CENTRAL_MANAGER_LEVEL)
	public ObjectiveRepresentor saveObjective(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline, Boolean confidentiality, String operator)
			throws AdaptorException {
		return (ObjectiveRepresentor) this.extensionManager.prepare(this.converter.toComplete(((id != null) && this.objectiveService.exists(id))
				? this.objectiveService.update(id, name, description, priority, ObjectiveStatus.valueOf(status.name()), deadline, confidentiality, operator)
				: this.objectiveService.create(name, description, priority, ObjectiveStatus.valueOf(status.name()), deadline, confidentiality, operator)));
	}

	@Override
	@Authorized(RestrictionLevel.CENTRAL_MANAGER_LEVEL)
	public void removeObjective(Long id) throws AdaptorException {
		try {
			this.objectiveService.delete(id);
		} catch(final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

}
