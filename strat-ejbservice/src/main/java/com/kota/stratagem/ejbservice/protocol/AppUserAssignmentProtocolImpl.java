package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.AssignmentConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserAssignmentService;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;

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

	@Override
	public AppUserObjectiveAssignmentRepresentor saveObjectiveAssignment(String entrustor, String recipient, ObjectiveRepresentor objective)
			throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create AppUser Objective Assignment (recipient: " + recipient + ", Objective: " + objective + ")");
			}
			return this.converter.to(this.assignmentService.create(this.appUserService.read(entrustor), this.appUserService.read(recipient),
					this.objectiveService.readElementary(objective.getId())));
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
