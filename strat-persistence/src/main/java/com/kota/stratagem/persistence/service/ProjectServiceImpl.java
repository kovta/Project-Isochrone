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

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.ProjectParameter;
import com.kota.stratagem.persistence.query.ProjectQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Contained
@Stateless(mappedName = "ejb/projectService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProjectServiceImpl implements ProjectService {

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@Override
	public Project create(String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, Long creator, Long objective) {
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
	}

	@Override
	public Project readElementary(Long id) {
		return this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID, Project.class).setParameter(ProjectParameter.ID, id).getSingleResult();
	}

	@Override
	public Project readWithAssignments(Long id) {
		return this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID_WITH_ASSIGNMENTS, Project.class).setParameter(ProjectParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Project readWithTasks(Long id) {
		return this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID_WITH_TASKS, Project.class).setParameter(ProjectParameter.ID, id).getSingleResult();
	}

	@Override
	public Project readWithSubmodules(Long id) {
		return this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID_WITH_SUBMODULES, Project.class).setParameter(ProjectParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Project readWithSubmodulesAndTasks(Long id) {
		return this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID_WITH_SUBMODULES_AND_TASKS, Project.class).setParameter(ProjectParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Project readComplete(Long id) {
		return this.entityManager.createNamedQuery(ProjectQuery.GET_BY_ID_COMPLETE, Project.class).setParameter(ProjectParameter.ID, id).getSingleResult();
	}

	@Override
	public Set<Project> readByStatus(ProjectStatus status) {
		return new HashSet<Project>(this.entityManager.createNamedQuery(ProjectQuery.GET_ALL_BY_STATUS, Project.class)
				.setParameter(ProjectParameter.STATUS, status).getResultList());
	}

	@Override
	public Set<Project> readAll() {
		return new HashSet<Project>(this.entityManager.createNamedQuery(ProjectQuery.GET_ALL_PROJECTS, Project.class).getResultList());
	}

	@Override
	public Project update(Long id, String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, String modifier) {
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
	}

	@Override
	public void delete(Long id) throws CoherentPersistenceServiceException {
		if (this.exists(id)) {
			if (this.readWithTasks(id).getTasks().size() == 0) {
				this.entityManager.createNamedQuery(ProjectQuery.REMOVE_BY_ID).setParameter(ProjectParameter.ID, id).executeUpdate();
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "Project has undeleted dependency(s)", id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Project doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) {
		return this.entityManager.createNamedQuery(ProjectQuery.COUNT_BY_ID, Long.class).setParameter(ProjectParameter.ID, id).getSingleResult() > 0;
	}

}