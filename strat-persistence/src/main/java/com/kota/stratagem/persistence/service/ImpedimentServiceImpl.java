package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Remedy;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.trunk.ImpedimentStatus;
import com.kota.stratagem.persistence.entity.trunk.Priority;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.ImpedimentParameter;
import com.kota.stratagem.persistence.query.ImpedimentQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Stateless(mappedName = "ejb/impedimentService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ImpedimentServiceImpl implements ImpedimentService {

	private static final Logger LOGGER = Logger.getLogger(ImpedimentServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@Override
	public Impediment create(String name, String description, Priority priority, ImpedimentStatus status, Date reportDate, AppUser reporter, AppUser processor,
			Set<Remedy> remedies, Project project, Task task) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Impediment (name=" + name + ", description=" + description + ", priority=" + priority + ", status=" + status + ", reportDate="
					+ reportDate + ", reporter=" + reporter + ")");
		}
		try {
			final Impediment impediment = new Impediment(name, description, priority, status, reportDate, processor, processor, remedies, project, task);
			this.entityManager.persist(impediment);
			this.entityManager.flush();
			return impediment;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting user (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Impediment read(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Impediment by id (" + id + ")");
		}
		Impediment result = null;
		try {
			result = this.entityManager.createNamedQuery(ImpedimentQuery.GET_BY_ID, Impediment.class).setParameter(ImpedimentParameter.ID, id)
					.getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Impediment by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Set<Impediment> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Fetching all Impediment");
		}
		Set<Impediment> result = null;
		try {
			result = new HashSet<Impediment>(this.entityManager.createNamedQuery(ImpedimentQuery.GET_ALL_IMPEDIMENTS, Impediment.class).getResultList());
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error occured while fetching Impediment" + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Impediment update(Long id, String name, String description, Priority priority, ImpedimentStatus status, Date reportDate, AppUser reporter,
			AppUser processor, Set<Remedy> remedies, Project project, Task task) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Impediment (id: " + id + ", name=" + name + ", description=" + description + ", priority=" + priority + ", status=" + status
					+ ", reportDate=" + reportDate + ", reporter=" + reporter + ")");
		}
		try {
			final Impediment impediment = this.read(id);
			impediment.setName(name);
			impediment.setDescription(description);
			impediment.setRemedies(remedies);
			impediment.setStatus(status);
			impediment.setReportDate(reportDate);
			impediment.setReporter(reporter);
			impediment.setProcessor(processor);
			impediment.setRemedies(remedies);
			impediment.setProject(project);
			impediment.setTask(task);
			return this.entityManager.merge(impediment);
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when merging AppUser! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void delete(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove Impediment by id (" + id + ")");
		}
		if (this.exists(id)) {
			if (this.read(id).getRemedies().size() == 0) {
				try {
					this.entityManager.createNamedQuery(ImpedimentQuery.REMOVE_BY_ID).setParameter(ImpedimentParameter.ID, id).executeUpdate();
				} catch (final Exception e) {
					throw new PersistenceServiceException("Unknown error when removing Impediment by id (" + id + ")! " + e.getLocalizedMessage(), e);
				}
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "Impediment has undeleted dependency(s)",
						id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Impediment doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Impediment by id (" + id + ")");
		}
		try {
			return this.entityManager.createNamedQuery(ImpedimentQuery.COUNT_BY_ID, Long.class).setParameter(ImpedimentParameter.ID, id).getSingleResult() == 1;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during counting Impediments by id: (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}
