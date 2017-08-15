package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.ObjectiveParameter;
import com.kota.stratagem.persistence.query.ObjectiveQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Stateless(mappedName = "ejb/objectiveService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ObjectiveServiceImpl implements ObjectiveService {

	private static final Logger LOGGER = Logger.getLogger(ObjectiveServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	private Objective retrieveSingleRecord(Long id, String query, String message) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}
		Objective result = null;
		try {
			result = this.entityManager.createNamedQuery(query, Objective.class).setParameter(ObjectiveParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Objective by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Objective create(String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality, String creator)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Objective (name=" + name + ", description=" + description + ", priority=" + priority + ", status=" + status + ", deadline="
					+ deadline + ", confidential=" + confidentiality + ", creator=" + creator + ")");
		}
		try {
			final AppUser operator = this.appUserService.readElementary(creator);
			final Objective objective = new Objective(name, description, priority, status, deadline, confidentiality, new Date(), new Date());
			objective.setCreator(operator);
			objective.setModifier(operator);
			this.entityManager.merge(objective);
			this.entityManager.flush();
			return objective;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Objective (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Objective readElementary(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ObjectiveQuery.GET_BY_ID, "Get Objective by id (" + id + ")");
	}

	@Override
	public Objective readWithTasks(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ObjectiveQuery.GET_BY_ID_WITH_TASKS, "Get Objective with Tasks by id (" + id + ")");
	}

	@Override
	public Objective readWithProjects(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ObjectiveQuery.GET_BY_ID_WITH_PROJECTS, "Get Objective with Projects by id (" + id + ")");
	}

	@Override
	public Objective readWithProjectsAndTasks(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ObjectiveQuery.GET_BY_ID_WITH_PROJECTS_AND_TASKS, "Get Objective with Projects and Tasks by id (" + id + ")");
	}

	@Override
	public Objective readComplete(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ObjectiveQuery.GET_BY_ID_COMPLETE, "Get Objective with all attributes by id (" + id + ")");
	}

	@Override
	public Set<Objective> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Fetching all Objectives");
		}
		Set<Objective> result = null;
		try {
			result = new HashSet<Objective>(this.entityManager.createNamedQuery(ObjectiveQuery.GET_ALL_OBJECTIVES, Objective.class).getResultList());
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error occured while fetching AppUsers" + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Objective update(Long id, String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality,
			String modifier) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Objective (id: " + id + ", name=" + name + ", description=" + description + ", priority=" + priority + ", status=" + status
					+ ", deadline=" + deadline + ", confidential=" + confidentiality + ")");
		}
		try {
			final Objective objective = this.readComplete(id);
			final AppUser operator = this.appUserService.readElementary(modifier);
			objective.setName(name);
			objective.setDescription(description);
			objective.setPriority(priority);
			objective.setStatus(status);
			objective.setDeadline(deadline);
			objective.setConfidential(confidentiality);
			if (objective.getCreator().getId() == operator.getId()) {
				objective.setModifier(objective.getCreator());
			} else if (objective.getModifier().getId() != operator.getId()) {
				objective.setModifier(operator);
			}
			objective.setModificationDate(new Date());
			return this.entityManager.merge(objective);
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when merging Project! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void delete(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove Objective by id (" + id + ")");
		}
		if (this.exists(id)) {
			final Objective objective = this.readWithProjectsAndTasks(id);
			if ((objective.getTasks().size() == 0) && (objective.getProjects().size() == 0)) {
				try {
					this.entityManager.createNamedQuery(ObjectiveQuery.REMOVE_BY_ID).setParameter(ObjectiveParameter.ID, id).executeUpdate();
				} catch (final Exception e) {
					throw new PersistenceServiceException("Unknown error when removing Objective by id (" + id + ")! " + e.getLocalizedMessage(), e);
				}
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "Objective has undeleted dependency(s)",
						id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Objective doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Objective by id (" + id + ")");
		}
		try {
			return this.entityManager.createNamedQuery(ObjectiveQuery.COUNT_BY_ID, Long.class).setParameter(ObjectiveParameter.ID, id).getSingleResult() == 1;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during Objective search (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}
