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
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.kota.stratagem.persistence.context.PersistenceServiceConfiguration;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.ObjectiveParameter;
import com.kota.stratagem.persistence.query.AppUserObjectiveAssignmentQuery;
import com.kota.stratagem.persistence.query.ObjectiveQuery;
import com.kota.stratagem.persistence.query.TeamObjectiveAssignmentQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Contained
@Stateless(mappedName = PersistenceServiceConfiguration.OBJECTIVE_SERVICE_SIGNATURE)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ObjectiveServiceImpl implements ObjectiveService {

	@Inject
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ProjectService projectService;

	@EJB
	private TaskService taskService;

	@Override
	public Objective create(String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality, String creator) {
		final AppUser operator = this.appUserService.readElementary(creator);
		final Objective objective = new Objective(name, description, priority, status, deadline, confidentiality, new Date(), new Date());
		objective.setCreator(operator);
		objective.setModifier(operator);
		this.entityManager.merge(objective);
		this.entityManager.flush();
		return objective;
	}

	@Override
	public Objective readElementary(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID, Objective.class).setParameter(ObjectiveParameter.ID, id).getSingleResult();
	}

	@Override
	public Objective readWithMonitoring(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID_WITH_MONITORING, Objective.class).setParameter(ObjectiveParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Objective readWithAssignments(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID_WITH_ASSIGNMENTS, Objective.class).setParameter(ObjectiveParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Objective readWithTasks(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID_WITH_TASKS, Objective.class).setParameter(ObjectiveParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Objective readWithProjects(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID_WITH_PROJECTS, Objective.class).setParameter(ObjectiveParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Objective readWithProjectsAndTasks(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID_WITH_PROJECTS_AND_TASKS, Objective.class).setParameter(ObjectiveParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Objective readComplete(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.GET_BY_ID_COMPLETE, Objective.class).setParameter(ObjectiveParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Set<Objective> readAll() {
		return new HashSet<Objective>(this.entityManager.createNamedQuery(ObjectiveQuery.GET_ALL_OBJECTIVES, Objective.class).getResultList());
	}

	@Override
	public Objective update(Long id, String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality,
			String modifier) {
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
	}

	@Override
	public void delete(Long id) throws CoherentPersistenceServiceException {
		if (this.exists(id)) {
			this.entityManager.createNamedQuery(TeamObjectiveAssignmentQuery.REMOVE_BY_OBJECTIVE_ID).setParameter(ObjectiveParameter.ID, id).executeUpdate();
			this.entityManager.createNamedQuery(AppUserObjectiveAssignmentQuery.REMOVE_BY_OBJECTIVE_ID).setParameter(ObjectiveParameter.ID, id).executeUpdate();
			final Objective objective = this.readWithProjectsAndTasks(id);
			for (final Project project : objective.getProjects()) {
				this.projectService.delete(project.getId());
			}
			for (final Task task : objective.getTasks()) {
				this.taskService.delete(task.getId());
			}
			this.entityManager.createNamedQuery(ObjectiveQuery.REMOVE_BY_ID).setParameter(ObjectiveParameter.ID, id).executeUpdate();
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Objective doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) {
		return this.entityManager.createNamedQuery(ObjectiveQuery.COUNT_BY_ID, Long.class).setParameter(ObjectiveParameter.ID, id).getSingleResult() == 1;
	}

}
