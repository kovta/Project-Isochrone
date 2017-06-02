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
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.AppUserParameter;
import com.kota.stratagem.persistence.query.AppUserQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Stateless(mappedName = "ejb/appUserService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AppUserServiceImpl implements AppUserService {

	private static final Logger LOGGER = Logger.getLogger(AppUserServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@Override
	public AppUser create(String name, String passwordHash, String email, Role role, Set<Objective> objectives, Set<Project> projects, Set<Task> tasks,
			Set<Impediment> reportedImpediments, Set<Impediment> processedImpediments, Set<Team> supervisedTeams, Set<Team> teamMemberships)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"Create AppUser (name=" + name + ", passwordHash=" + passwordHash + ", email=" + email + ", role=" + role + ", projects=" + projects + ")");
		}
		try {
			final AppUser user = new AppUser(name, passwordHash, email, role, objectives, projects, tasks, reportedImpediments, processedImpediments,
					supervisedTeams, teamMemberships);
			this.entityManager.persist(user);
			this.entityManager.flush();
			return user;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting user (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public AppUser read(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get AppUser by id (" + id + ")");
		}
		AppUser result = null;
		try {
			result = this.entityManager.createNamedQuery(AppUserQuery.GET_BY_ID, AppUser.class).setParameter(AppUserParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching AppUser by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Set<AppUser> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Fetching all AppUsers");
		}
		Set<AppUser> result = null;
		try {
			result = new HashSet<AppUser>(this.entityManager.createNamedQuery(AppUserQuery.GET_ALL_USERS, AppUser.class).getResultList());
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error occured while fetching AppUsers" + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public AppUser update(Long id, String name, String passwordHash, String email, Role role, Set<Objective> objectives, Set<Project> projects, Set<Task> tasks,
			Set<Impediment> reportedImpediments, Set<Impediment> processedImpediments, Set<Team> supervisedTeams, Set<Team> teamMemberships)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update ApUser (id: " + id + ", name=" + name + ", passwordHash=" + passwordHash + ", email=" + email + ", role=" + role
					+ ", projects=" + projects + ")");
		}
		try {
			final AppUser user = this.read(id);
			user.setName(name);
			user.setPasswordHash(passwordHash);
			user.setEmail(email);
			user.setRole(role);
			user.setObjectives(objectives != null ? objectives : new HashSet<Objective>());
			user.setProjects(projects != null ? projects : new HashSet<Project>());
			user.setTasks(tasks != null ? tasks : new HashSet<Task>());
			user.setReportedImpediments(reportedImpediments != null ? reportedImpediments : new HashSet<Impediment>());
			user.setProcessedImpediments(processedImpediments != null ? processedImpediments : new HashSet<Impediment>());
			user.setSupervisedTeams(supervisedTeams != null ? supervisedTeams : new HashSet<Team>());
			user.setTeamMemberships(teamMemberships != null ? teamMemberships : new HashSet<Team>());
			return this.entityManager.merge(user);
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when merging AppUser! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void delete(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove AppUser by id (" + id + ")");
		}
		if (this.exists(id)) {
			if (this.read(id).getProjects().size() == 0) {
				try {
					this.entityManager.createNamedQuery(AppUserQuery.REMOVE_BY_ID).setParameter(AppUserParameter.ID, id).executeUpdate();
				} catch (final Exception e) {
					throw new PersistenceServiceException("Unknown error when removing AppUser by id (" + id + ")! " + e.getLocalizedMessage(), e);
				}
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "AppUser has undeleted dependency(s)", id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "AppUser doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check AppUser by id (" + id + ")");
		}
		try {
			return this.entityManager.createNamedQuery(AppUserQuery.COUNT_BY_ID, Long.class).setParameter(AppUserParameter.ID, id).getSingleResult() == 1;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during counting Tasks by AppUser (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}
