package com.kota.stratagem.persistence.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.context.PersistenceServiceConfiguration;
import com.kota.stratagem.persistence.entity.AbstractAppUserAssignment;
import com.kota.stratagem.persistence.entity.AbstractMonitoredEntity;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.query.AppUserObjectiveAssignmentQuery;
import com.kota.stratagem.persistence.query.AppUserProjectAssignmentQuery;
import com.kota.stratagem.persistence.query.AppUserSubmoduleAssignmentQuery;
import com.kota.stratagem.persistence.query.AppUserTaskAssignmentQuery;
import com.kota.stratagem.persistence.util.Constants;

@Stateless(mappedName = PersistenceServiceConfiguration.APP_USER_ASSIGNMENT_SERVICE_SIGNATURE)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AppUserAssignmentServiceImpl implements AppUserAssignmentService {

	private static final Logger LOGGER = Logger.getLogger(AppUserAssignmentServiceImpl.class);

	@Inject
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private TaskService taskService;

	private <T extends AbstractMonitoredEntity, E extends AbstractAppUserAssignment> void persistAssignment(E subject, T object, Long entrustor, Long recipient)
			throws PersistenceServiceException {
		subject.setEntrustor(this.mergeOperators(entrustor, object));
		subject.setRecipient(this.mergeOperators(recipient, object));
		this.entityManager.persist(subject);
		this.entityManager.flush();
	}

	private <T extends AbstractMonitoredEntity> AppUser mergeOperators(Long subject, T object) throws PersistenceServiceException {
		if (object.getCreator().getId() == subject) {
			return object.getCreator();
		} else if (object.getModifier().getId() == subject) {
			return object.getModifier();
		} else {
			return this.appUserService.readElementary(subject);
		}
	}

	@Override
	public AppUserObjectiveAssignment createObjectiveAssignment(Long entrustor, Long recipient, Long objective) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create User Objective Assignment (objective=" + objective + ", recipient=" + recipient + ", entrustor=" + entrustor + ")");
		}
		try {
			final Objective targetObjective = this.objectiveService.readElementary(objective);
			final AppUserObjectiveAssignment assignment = new AppUserObjectiveAssignment(targetObjective, new Date());
			this.persistAssignment(assignment, targetObjective, entrustor, recipient);
			return assignment;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting User Objective Assignment (objective=" + objective + ", recipient="
					+ recipient + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public AppUserProjectAssignment createProjectAssignment(Long entrustor, Long recipient, Long project) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create User Project Assignment (project=" + project + ", recipient=" + recipient + ", entrustor=" + entrustor + ")");
		}
		try {
			final Project targetProject = this.projectService.readElementary(project);
			final AppUserProjectAssignment assignment = new AppUserProjectAssignment(targetProject, new Date());
			this.persistAssignment(assignment, targetProject, entrustor, recipient);
			return assignment;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting User Project Assignment (project=" + project + ", recipient=" + recipient
					+ ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public AppUserSubmoduleAssignment createSubmoduleAssignment(Long entrustor, Long recipient, Long submodule) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create User Objective Assignment (submodule=" + submodule + ", recipient=" + recipient + ", entrustor=" + entrustor + ")");
		}
		try {
			final Submodule targetSubmodule = this.submoduleService.readElementary(submodule);
			final AppUserSubmoduleAssignment assignment = new AppUserSubmoduleAssignment(targetSubmodule, new Date());
			this.persistAssignment(assignment, targetSubmodule, entrustor, recipient);
			return assignment;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting User Submodule Assignment (submodule=" + submodule + ", recipient="
					+ recipient + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public AppUserTaskAssignment createTaskAssignment(Long entrustor, Long recipient, Long task) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create User Task Assignment (task=" + task + ", recipient=" + recipient + ", entrustor=" + entrustor + ")");
		}
		try {
			final Task targetTask = this.taskService.readElementary(task);
			final AppUserTaskAssignment assignment = new AppUserTaskAssignment(targetTask, new Date());
			this.persistAssignment(assignment, targetTask, entrustor, recipient);
			return assignment;
		} catch (final Exception e) {
			throw new PersistenceServiceException(
					"Unknown error during persisting User Task Assignment (task=" + task + ", recipient=" + recipient + ")! " + e.getLocalizedMessage(), e);
		}
	}

	private Object readAssignment(Long id, String query, String object, Class<? extends AbstractAppUserAssignment> assignmentType)
			throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Read User " + object + " Assignment by id (" + id + ")");
		}
		Object result = null;
		try {
			result = this.entityManager.createNamedQuery(query, assignmentType).setParameter(AssignmentParameter.ID, id).getSingleResult();
		} catch (final Exception e) {
			throw new PersistenceServiceException(
					"Unknown error while retrieving User " + object + " Assignment by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	public AppUserObjectiveAssignment readObjectiveAssignment(Long id) throws PersistenceServiceException {
		return (AppUserObjectiveAssignment) this.readAssignment(id, AppUserObjectiveAssignmentQuery.GET_BY_ID, Constants.OBJECTIVE_DATA_LABEL,
				AppUserObjectiveAssignment.class);
	}

	@Override
	public AppUserProjectAssignment readProjectAssignment(Long id) throws PersistenceServiceException {
		return (AppUserProjectAssignment) this.readAssignment(id, AppUserProjectAssignmentQuery.GET_BY_ID, Constants.PROJECT_DATA_LABEL,
				AppUserProjectAssignment.class);
	}

	@Override
	public AppUserSubmoduleAssignment readSubmoduleAssignment(Long id) throws PersistenceServiceException {
		return (AppUserSubmoduleAssignment) this.readAssignment(id, AppUserSubmoduleAssignmentQuery.GET_BY_ID, Constants.SUBMODULE_DATA_LABEL,
				AppUserSubmoduleAssignment.class);
	}

	@Override
	public AppUserTaskAssignment readTaskAssignment(Long id) throws PersistenceServiceException {
		return (AppUserTaskAssignment) this.readAssignment(id, AppUserTaskAssignmentQuery.GET_BY_ID, Constants.TASK_DATA_LABEL, AppUserTaskAssignment.class);
	}

	private void removeAssignment(Long id, String query, String object) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Delete User " + object + " Assignment by id (" + id + ")");
		}
		try {
			this.entityManager.createNamedQuery(query).setParameter(AssignmentParameter.ID, id).executeUpdate();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error while deleting User " + object + " Assignment by id (" + id + ")! " + e.getLocalizedMessage(),
					e);
		}
	}

	@Override
	public void deleteObjectiveAssignment(Long id) throws PersistenceServiceException {
		this.removeAssignment(id, AppUserObjectiveAssignmentQuery.REMOVE_BY_ID, Constants.OBJECTIVE_DATA_LABEL);
	}

	@Override
	public void deleteProjectAssignment(Long id) throws PersistenceServiceException {
		this.removeAssignment(id, AppUserProjectAssignmentQuery.REMOVE_BY_ID, Constants.PROJECT_DATA_LABEL);
	}

	@Override
	public void deleteSubmoduleAssignment(Long id) throws PersistenceServiceException {
		this.removeAssignment(id, AppUserSubmoduleAssignmentQuery.REMOVE_BY_ID, Constants.SUBMODULE_DATA_LABEL);
	}

	@Override
	public void deleteTaskAssignment(Long id) throws PersistenceServiceException {
		this.removeAssignment(id, AppUserTaskAssignmentQuery.REMOVE_BY_ID, Constants.TASK_DATA_LABEL);
	}

}
