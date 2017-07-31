package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.AssignmentConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserAssignmentService;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;

@Stateless(mappedName = "ejb/appUserAssignmentProtocol")
public class AppUserAssignmentProtocolImpl implements AppUserAssignmentProtocol {

	private static final Logger LOGGER = Logger.getLogger(AppUserAssignmentProtocolImpl.class);

	@EJB
	AppUserAssignmentService assignmentService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private AssignmentConverter converter;

	@EJB
	private SessionContextAccessor sessionContextAccessor;

	@Override
	public void saveObjectiveAssignments(String[] recipients, Long objective) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create AppUser Objective Assignment (" + recipients.length + " recipients, Objective: " + objective + ")");
			}
			final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
			for (final String recipient : recipients) {
				this.assignmentService.create(this.appUserService.read(operator).getId(), this.appUserService.read(recipient).getId(), objective);
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeObjectiveAssignment(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove AppUser Objective Assignment (id: " + id + ")");
			}
			this.assignmentService.deleteObjectiveAssignment(id);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}
