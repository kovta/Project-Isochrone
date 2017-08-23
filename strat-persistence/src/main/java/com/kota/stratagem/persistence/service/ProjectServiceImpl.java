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
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.ProjectParameter;
import com.kota.stratagem.persistence.query.ProjectQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Stateless(mappedName = "ejb/projectService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProjectServiceImpl implements ProjectService {

	private static final Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	private Project retrieveSingleRecord(Long id, String query, String message) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}
		Project result = null;
		try {
			result = this.entityManager.createNamedQuery(query, Project.class).setParameter(ProjectParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Project by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Project create(String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, Long creator, Long objective)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"Create Project (name: " + name + ", description: " + description + ", status: " + status + ", confidentiality: " + confidentiality + ")");
		}
		try {
			final Objective parentObjective = this.objectiveService.readElementary(objective);
			final Project project = new Project(name, description, status, deadline, confidentiality, new Date(), new Date(), parentObjective);
			AppUser operatorTemp;
			if (parentObjective.getCreator().getId() == creator) {
				operatorTemp = parentObjective.getCreator();
			} else {
				operatorTemp = this.appUserService.readElementary(creator);
			}
			final AppUser operator = operatorTemp;
			project.setCreator(operator);
			project.setModifier(operator);
			this.entityManager.merge(project);
			this.entityManager.flush();
			return project;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Project (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Project readElementary(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ProjectQuery.GET_BY_ID, "Get Project by id (" + id + ")");
	}

	@Override
	public Project readWithAssignments(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ProjectQuery.GET_BY_ID_WITH_ASSIGNMENTS, "Get Project with Assignments by id (" + id + ")");
	}

	@Override
	public Project readWithTasks(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ProjectQuery.GET_BY_ID_WITH_TASKS, "Get Project with Tasks by id (" + id + ")");
	}

	@Override
	public Project readWithSubmodules(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ProjectQuery.GET_BY_ID_WITH_SUBMODULES, "Get Project with Submodules by id (" + id + ")");
	}

	@Override
	public Project readWithSubmodulesAndTasks(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ProjectQuery.GET_BY_ID_WITH_SUBMODULES_AND_TASKS, "Get Project with Submodules and Tasks by id (" + id + ")");
	}

	@Override
	public Project readComplete(Long id) throws PersistenceServiceException {
		return this.retrieveSingleRecord(id, ProjectQuery.GET_BY_ID_COMPLETE, "Get Project with all attributes by id (" + id + ")");
	}

	@Override
	public Set<Project> readByStatus(ProjectStatus status) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Projects by Status: " + status);
		}
		Set<Project> result = null;
		try {
			result = new HashSet<Project>(this.entityManager.createNamedQuery(ProjectQuery.GET_ALL_BY_STATUS, Project.class)
					.setParameter(ProjectParameter.STATUS, status).getResultList());
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Projects! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Set<Project> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Fetching all Projects");
		}
		Set<Project> result = null;
		try {
			result = new HashSet<Project>(this.entityManager.createNamedQuery(ProjectQuery.GET_ALL_PROJECTS, Project.class).getResultList());
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error occured while fetching Projects" + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Project update(Long id, String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, String modifier)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Project (id: " + id + ", name: " + name + ", description: " + description + ", status: " + status + ", confidentiality: "
					+ confidentiality + ")");
		}
		try {
			final Project project = this.readComplete(id);
			final AppUser operator = this.appUserService.readElementary(modifier);
			project.setName(name);
			project.setDescription(description);
			project.setStatus(status);
			project.setDeadline(deadline);
			project.setConfidential(confidentiality);
			if (project.getCreator().getId() == operator.getId()) {
				project.setModifier(project.getCreator());
			} else if (project.getModifier().getId() != operator.getId()) {
				project.setModifier(operator);
			}
			project.setModificationDate(new Date());
			return this.entityManager.merge(project);
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when merging Project! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void delete(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove Project by id (" + id + ")");
		}
		if (this.exists(id)) {
			if (this.readWithTasks(id).getTasks().size() == 0) {
				try {
					this.entityManager.createNamedQuery(ProjectQuery.REMOVE_BY_ID).setParameter(ProjectParameter.ID, id).executeUpdate();
				} catch (final Exception e) {
					throw new PersistenceServiceException("Unknown error when removing Project by id (" + id + ")! " + e.getLocalizedMessage(), e);
				}
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "Project has undeleted dependency(s)", id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Project doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Project by id (" + id + ")");
		}
		try {
			return this.entityManager.createNamedQuery(ProjectQuery.COUNT_BY_ID, Long.class).setParameter(ProjectParameter.ID, id).getSingleResult() > 0;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during Project serach (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}