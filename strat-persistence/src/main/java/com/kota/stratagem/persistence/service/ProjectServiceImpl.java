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
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
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

	@Override
	public Project create(String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, AppUser creator, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers, Set<Impediment> impediments, Long objective) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Project (name: " + name + ", description: " + description + ", status: " + status + ", tasks: " + tasks + ", confidentiality: "
					+ confidentiality + ")");
		}
		try {
			final Objective parentObjective = this.objectiveService.readWithProjects(objective);
			final Project project = new Project(name, description, status, deadline, confidentiality, new Date(), new Date(), tasks, assignedTeams,
					assignedUsers, impediments, parentObjective);
			AppUser operatorTemp;
			if (parentObjective.getCreator().getId() == creator.getId()) {
				operatorTemp = parentObjective.getCreator();
			} else {
				operatorTemp = this.appUserService.read(creator.getId());
			}
			final AppUser operator = operatorTemp;
			project.setCreator(operator);
			project.setModifier(operator);
			parentObjective.addProject(project);
			this.entityManager.merge(project);
			this.entityManager.flush();
			return project;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Project (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Project readElementary(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Project by id (" + id + ")");
		}
		Project result = null;
		try {
			result = this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID, Project.class).setParameter(ProjectParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Project by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Project readWithTasks(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Project with Tasks by id (" + id + ")");
		}
		Project result = null;
		try {
			result = this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID_WITH_TASKS, Project.class).setParameter(ProjectParameter.ID, id)
					.getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Project by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Set<Project> readByStatus(ProjectStatus status) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Projects by Status");
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
	public Project update(Long id, String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, AppUser modifier,
			Set<Task> tasks, Set<Team> assignedTeams, Set<AppUser> assignedUsers, Set<Impediment> impediments, Long objective)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Project (id: " + id + ", name: " + name + ", description: " + description + ", status: " + status + ", tasks: " + tasks
					+ ", confidentiality: " + confidentiality + ")");
		}
		try {
			final Project project = this.readElementary(id);
			final AppUser operator = this.appUserService.read(modifier.getId());
			final Objective parentObjective = this.objectiveService.readWithProjects(objective);
			project.setName(name);
			project.setDescription(description);
			project.setStatus(status);
			project.setDeadline(deadline);
			project.setConfidential(confidentiality);
			if (!(project.getModifier().equals(operator))) {
				if (!(project.getCreator().equals(project.getModifier()))) {
					project.setModifier(operator);
				} else if (project.getCreator().equals(operator)) {
					project.setModifier(project.getCreator());
				}
			}
			project.setModificationDate(new Date());
			project.setTasks(tasks != null ? tasks : new HashSet<Task>());
			project.setAssignedTeams(assignedTeams != null ? assignedTeams : new HashSet<Team>());
			project.setAssignedUsers(assignedUsers != null ? assignedUsers : new HashSet<AppUser>());
			project.setImpediments(impediments != null ? impediments : new HashSet<Impediment>());
			project.setObjective(parentObjective);
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
			return this.entityManager.createNamedQuery(ProjectQuery.COUNT_BY_ID, Long.class).setParameter(ProjectParameter.ID, id).getSingleResult() == 1;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during Project serach (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}