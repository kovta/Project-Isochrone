package com.kota.stratagem.persistence.service.delegation.individual;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.entity.AbstractAppUserAssignment;
import com.kota.stratagem.persistence.entity.AbstractMonitoredEntity;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.service.AppUserService;

public abstract class AbstractAppUserAssignmentService implements AppUserAssignmentService {

	private static final Logger LOGGER = Logger.getLogger(AbstractAppUserAssignmentService.class);

	@Inject
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	private <T extends AbstractMonitoredEntity> AppUser mergeOperators(Long subject, T object) throws PersistenceServiceException {
		if (object.getCreator().getId() == subject) {
			return object.getCreator();
		} else if (object.getModifier().getId() == subject) {
			return object.getModifier();
		} else {
			return this.appUserService.readElementary(subject);
		}
	}

	@SuppressWarnings("unused")
	private <T extends AbstractMonitoredEntity, E extends AbstractAppUserAssignment> void persistAssignment(E subject, T object, Long entrustor, Long recipient)
			throws PersistenceServiceException {
		subject.setEntrustor(this.mergeOperators(entrustor, object));
		subject.setRecipient(this.mergeOperators(recipient, object));
		this.entityManager.persist(subject);
		this.entityManager.flush();
	}

	@SuppressWarnings("unused")
	private void removeAssignment(Long id, String query, String object) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove User " + object + " Assignment by id (" + id + ")");
		}
		try {
			this.entityManager.createNamedQuery(query).setParameter(AssignmentParameter.ID, id).executeUpdate();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when removing  User " + object + " Assignment by id (" + id + ")! " + e.getLocalizedMessage(),
					e);
		}
	}

}
