package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.SubmoduleService;

@Regulated
@Stateless(mappedName = EJBServiceConfiguration.SUBMODULE_PROTOCOL_SIGNATURE)
public class SubmoduleProtocolImpl implements SubmoduleProtocol {

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private AppUserService appUserService;

	@Inject
	private SubmoduleConverter submoduleConverter;

	@Inject
	@SubmoduleOriented
	private DTOExtensionManager extensionManager;

	@Override
	public SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException {
		return (SubmoduleRepresentor) this.extensionManager.prepare(this.submoduleConverter.toComplete(this.submoduleService.readComplete(id)));
	}

	@Override
	public List<SubmoduleRepresentor> getAllSubmodules() throws AdaptorException {
		return new ArrayList<SubmoduleRepresentor>(this.submoduleConverter.toSimplified(this.submoduleService.readAll()));
	}

	@Override
	public SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Long project) throws AdaptorException {
		return (SubmoduleRepresentor) this.extensionManager.prepare(this.submoduleConverter.toComplete(((id != null) && this.submoduleService.exists(id))
				? this.submoduleService.update(id, name, description, deadline, this.appUserService.readElementary(operator))
				: this.submoduleService.create(name, description, deadline, this.appUserService.readElementary(operator), project)));
	}

	@Override
	public void removeSubmodule(Long id) throws AdaptorException {
		try {
			this.submoduleService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

}
