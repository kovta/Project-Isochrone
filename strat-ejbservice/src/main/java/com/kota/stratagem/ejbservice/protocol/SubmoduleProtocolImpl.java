package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.SubmoduleService;

@Stateless(mappedName = "ejb/submoduleProtocol")
public class SubmoduleProtocolImpl implements SubmoduleProtocol {

	private static final Logger LOGGER = Logger.getLogger(SubmoduleProtocolImpl.class);

	@EJB
	private SubmoduleService submoduleSerive;

	@EJB
	private AppUserService appUserService;

	@EJB
	private SubmoduleConverter submoduleConverter;

	@EJB
	private ProjectConverter projectConverter;

	@Override
	public SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException {
		try {
			final SubmoduleRepresentor representor = this.submoduleConverter.toComplete(this.submoduleSerive.readComplete(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Submodule (id: " + id + ") --> " + representor);
			}
			Collections.sort(representor.getTasks(), new Comparator<TaskRepresentor>() {
				@Override
				public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
					final int c = (int) obj_a.getCompletion() - (int) obj_b.getCompletion();
					if (c == 0) {
						return obj_a.getName().compareTo(obj_b.getName());
					}
					return c * -1;
				}
			});
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<SubmoduleRepresentor> getAllSubmodules() throws AdaptorException {
		Set<SubmoduleRepresentor> representors = new HashSet<>();
		try {
			representors = this.submoduleConverter.toSimplified(this.submoduleSerive.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Submodules --> " + representors.size() + " item(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
		return new ArrayList<SubmoduleRepresentor>(representors);
	}

	@Override
	public SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Long project) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(id != null ? "Update Submodule (id: " + id + ")" : "Create Submodule (" + name + ")");
			}
			return this.submoduleConverter.toComplete(((id != null) && this.submoduleSerive.exists(id))
					? this.submoduleSerive.update(id, name, description, deadline, this.appUserService.readElementary(operator))
					: this.submoduleSerive.create(name, description, deadline, this.appUserService.readElementary(operator), project));
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeSubmodule(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove Submodule (id: " + id + ")");
			}
			this.submoduleSerive.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}
