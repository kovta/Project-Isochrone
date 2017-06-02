package com.kota.stratagem.persistence.service;

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
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
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

	@Override
	public Objective create(String name, String description, int priority, ObjectiveStatus status, Set<Project> projects, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Objective (name: " + name + ", description: " + description + ", priority: " + priority + ", status: " + status + ", tasks: "
					+ tasks + ")");
		}
		try {
			final Objective objective = new Objective(name, description, priority, status, projects, tasks, assignedTeams, assignedUsers);
			this.entityManager.persist(objective);
			this.entityManager.flush();
			return objective;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Objective (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Objective readElementary(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Objective by id (" + id + ")");
		}
		Objective result = null;
		try {
			result = this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID, Objective.class).setParameter(ObjectiveParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Objective by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Objective readWithProjectsAndTasks(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Objective with projects and tasks by id (" + id + ")");
		}
		Objective result = null;
		try {
			result = this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID_WITH_PROJECTS_AND_TASKS, Objective.class)
					.setParameter(ObjectiveParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Objective by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
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
	public Objective update(Long id, String name, String description, int priority, ObjectiveStatus status, Set<Project> projects, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Objective (id: " + id + ", name: " + name + ", description: " + description + ", priority: " + priority + ", status: " + status
					+ ", tasks: " + tasks + ")");
		}
		try {
			final Objective objective = this.readElementary(id);
			objective.setName(name);
			objective.setDescription(description);
			objective.setPriority(priority);
			objective.setStatus(status);
			objective.setProjects(projects != null ? projects : new HashSet<Project>());
			objective.setTasks(tasks != null ? tasks : new HashSet<Task>());
			objective.setAssignedTeams(assignedTeams != null ? assignedTeams : new HashSet<Team>());
			objective.setAssignedUsers(assignedUsers != null ? assignedUsers : new HashSet<AppUser>());
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
