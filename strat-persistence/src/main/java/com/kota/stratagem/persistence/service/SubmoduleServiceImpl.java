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
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.SubmoduleParameter;
import com.kota.stratagem.persistence.query.SubmoduleQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Stateless(mappedName = "ejb/submoduleService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SubmoduleServiceImpl implements SubmoduleService {

	private static final Logger LOGGER = Logger.getLogger(SubmoduleServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ProjectService projectService;

	@Override
	public Submodule create(String name, String description, Date deadline, AppUser creator, Date creationDate, Set<Task> tasks, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers, Long project) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Submodule (name: " + name + ", description: " + description + ", tasks: " + tasks + ")");
		}
		try {
			final Project parentProject = this.projectService.readElementary(project);
			final Submodule submodule = new Submodule(name, description, deadline, new Date(), new Date(), tasks, assignedTeams, assignedUsers, parentProject);
			AppUser operatorTemp;
			if (parentProject.getCreator().getId() == creator.getId()) {
				operatorTemp = parentProject.getCreator();
			} else {
				operatorTemp = this.appUserService.read(creator.getId());
			}
			final AppUser operator = operatorTemp;
			submodule.setCreator(operator);
			submodule.setModifier(operator);
			this.entityManager.merge(submodule);
			this.entityManager.flush();
			return submodule;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Submodule (" + name + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Submodule readElementary(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Submodule by id (" + id + ")");
		}
		Submodule result = null;
		try {
			result = this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Submodule by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Submodule readWithTasks(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Submodule with Tasks by id (" + id + ")");
		}
		Submodule result = null;
		try {
			result = this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_TASKS, Submodule.class).setParameter(SubmoduleParameter.ID, id)
					.getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Submodule by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Set<Submodule> readAll() throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Fetching all Submodules");
		}
		Set<Submodule> result = null;
		try {
			result = new HashSet<Submodule>(this.entityManager.createNamedQuery(SubmoduleQuery.GET_ALL_SUBMODULES, Submodule.class).getResultList());
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error occured while fetching Submodules" + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public Submodule update(Long id, String name, String description, Date deadline, AppUser modifier, Date modificationDate, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Submodule (id: " + id + ", name: " + name + ", description: " + description + ", tasks: " + tasks + ")");
		}
		try {
			final Submodule submodule = this.readWithTasks(id);
			final AppUser operator = this.appUserService.read(modifier.getId());
			submodule.setName(name);
			submodule.setDescription(description);
			submodule.setDeadline(deadline);
			if (!(submodule.getModifier().equals(operator))) {
				if (!(submodule.getCreator().equals(submodule.getModifier()))) {
					submodule.setModifier(operator);
				} else if (submodule.getCreator().equals(operator)) {
					submodule.setModifier(submodule.getCreator());
				}
			}
			submodule.setModificationDate(new Date());
			// project.setSubmodules(submodules != null ? submodules : new HashSet<Submodule>());
			// project.setTasks(tasks != null ? tasks : new HashSet<Task>());
			submodule.setAssignedTeams(assignedTeams != null ? assignedTeams : new HashSet<Team>());
			submodule.setAssignedUsers(assignedUsers != null ? assignedUsers : new HashSet<AppUser>());
			return this.entityManager.merge(submodule);
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error when merging Submodule! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void delete(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Remove Submodule by id (" + id + ")");
		}
		if (this.exists(id)) {
			if (this.readWithTasks(id).getTasks().size() == 0) {
				try {
					this.entityManager.createNamedQuery(SubmoduleQuery.REMOVE_BY_ID).setParameter(SubmoduleParameter.ID, id).executeUpdate();
				} catch (final Exception e) {
					throw new PersistenceServiceException("Unknown error when removing Submodule by id (" + id + ")! " + e.getLocalizedMessage(), e);
				}
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "Project has undeleted dependency(s)", id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Project doesn't exist", id.toString());
		}
	}

	@Override
	public boolean exists(Long id) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check Submodule by id (" + id + ")");
		}
		try {
			// query returns more than 1 result
			return this.entityManager.createNamedQuery(SubmoduleQuery.COUNT_BY_ID, Long.class).setParameter(SubmoduleParameter.ID, id).getSingleResult() > 0;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during Submodule serach (" + id + ")! " + e.getLocalizedMessage(), e);
		}
	}

}
