package com.kota.stratagem.persistence.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.query.UserObjectiveAssignmentQuery;

@Stateless(mappedName = "ejb/appUserAssignmentService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AppUserAssignmentServiceImpl implements AppUserAssignmentService {

	private static final Logger LOGGER = Logger.getLogger(AppUserAssignmentServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@Override
	public AppUserObjectiveAssignment create(AppUser entrustor, AppUser recipient, Objective objective) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create User Objective Assignment (objective=" + objective + ", recipient=" + recipient + ", entrustor=" + entrustor + ")");
		}
		try {
			final AppUserObjectiveAssignment assignment = new AppUserObjectiveAssignment(entrustor, recipient, objective, new Date());
			this.entityManager.merge(assignment);
			this.entityManager.flush();
			return assignment;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting  User Objective Assignment (objective=" + objective + ", recipient="
					+ recipient + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void deleteObjectiveAssignment(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove User Objective Assignment by id (" + id + ")");
		}
		try {
			this.entityManager.createNamedQuery(UserObjectiveAssignmentQuery.REMOVE_BY_ID).setParameter(AssignmentParameter.ID, id).executeUpdate();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when removing  User Objective Assignment by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}
