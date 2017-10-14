package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.kota.stratagem.persistence.context.PersistenceServiceConfiguration;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.SubmoduleParameter;
import com.kota.stratagem.persistence.query.AppUserSubmoduleAssignmentQuery;
import com.kota.stratagem.persistence.query.SubmoduleQuery;
import com.kota.stratagem.persistence.query.TeamSubmoduleAssignmentQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Contained
@Stateless(mappedName = PersistenceServiceConfiguration.SUBMODULE_SERVICE_SIGNATURE)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SubmoduleServiceImpl extends IntegrationDependencyContainer implements SubmoduleService {

	@Override
	public Submodule create(String name, String description, Date deadline, String creator, Long project) {
		final Project parentProject = this.projectService.readElementary(project);
		final AppUser operator = this.appUserService.readElementary(creator);
		final Submodule submodule = new Submodule(name, description, deadline, new Date(), new Date(), parentProject);
		submodule.setCreator(operator);
		submodule.setModifier(operator);
		this.entityManager.merge(submodule);
		this.entityManager.flush();
		return submodule;
	}

	@Override
	public Submodule readElementary(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readWithMonitoring(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_MONITORING, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readWithAssignments(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_ASSIGNMENTS, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readWithTasks(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_TASKS, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readWithDependencies(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_DEPENDENCIES, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readWithDependants(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_DEPENDANTS, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readWithDirectDependencies(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_DIRECT_DEPENDENCIES, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readWithTasksAndDirectDependencies(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_TASKS_AND_DIRECT_DEPENDENCIES, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Submodule readComplete(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_COMPLETE, Submodule.class).setParameter(SubmoduleParameter.ID, id).getSingleResult();
	}

	@Override
	public Set<Submodule> readAll() {
		return new HashSet<Submodule>(this.entityManager.createNamedQuery(SubmoduleQuery.GET_ALL_SUBMODULES, Submodule.class).getResultList());
	}

	@Override
	public Submodule update(Long id, String name, String description, Date deadline, String modifier) {
		final Submodule submodule = this.readComplete(id);
		final AppUser operator = this.appUserService.readElementary(modifier);
		submodule.setName(name);
		submodule.setDescription(description);
		submodule.setDeadline(deadline);
		if(submodule.getCreator().getId() == operator.getId()) {
			submodule.setModifier(submodule.getCreator());
		} else if(submodule.getModifier().getId() != operator.getId()) {
			submodule.setModifier(operator);
		}
		submodule.setModificationDate(new Date());
		return this.entityManager.merge(submodule);
	}

	@Override
	public void delete(Long id) throws CoherentPersistenceServiceException {
		if(this.exists(id)) {
			this.entityManager.createNamedQuery(TeamSubmoduleAssignmentQuery.REMOVE_BY_SUBMODULE_ID).setParameter(SubmoduleParameter.ID, id).executeUpdate();
			this.entityManager.createNamedQuery(AppUserSubmoduleAssignmentQuery.REMOVE_BY_SUBMODULE_ID).setParameter(SubmoduleParameter.ID, id).executeUpdate();
			final Submodule submodule = this.readWithTasks(id);
			if(submodule.getTasks() != null) {
				for(final Task task : submodule.getTasks()) {
					this.taskService.delete(task.getId());
				}
			}
			this.entityManager.createNamedQuery(SubmoduleQuery.REMOVE_BY_ID).setParameter(SubmoduleParameter.ID, id).executeUpdate();
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Project doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.COUNT_BY_ID, Long.class).setParameter(SubmoduleParameter.ID, id).getSingleResult() > 0;
	}

	@Override
	public void createDependency(Long dependency, Long dependant, Long operator) {
		final Submodule maintainer = this.readWithDependencies(dependant);
		final Submodule satiator = this.readWithDependencies(dependency);
		final Set<Submodule> dependencies = this.manageOperators(satiator, maintainer, operator);
		dependencies.add(this.readElementary(dependency));
		maintainer.setSubmoduleDependencies(dependencies);
		this.entityManager.merge(maintainer);
	}

	@Override
	public void deleteDependency(Long dependency, Long dependant, Long operator) {
		final Submodule maintainer = this.readWithDependencies(dependant);
		final Submodule satiator = this.readWithDependencies(dependency);
		final Set<Submodule> dependencies = this.manageOperators(satiator, maintainer, operator);
		dependencies.remove(this.readElementary(dependency));
		maintainer.setSubmoduleDependencies(dependencies);
		this.entityManager.merge(maintainer);
	}

	private Set<Submodule> manageOperators(Submodule satiator, Submodule maintainer, Long operator) {
		if(maintainer.getCreator().getId() == operator) {
			maintainer.setModifier(maintainer.getCreator());
		} else if(maintainer.getModifier().getId() != operator) {
			maintainer.setModifier(this.appUserService.readElementary(operator));
		}
		maintainer.setModificationDate(new Date());
		if(satiator.getCreator().getId() == operator) {
			satiator.setModifier(satiator.getCreator());
		} else if(satiator.getModifier().getId() != operator) {
			satiator.setModifier(this.appUserService.readElementary(operator));
		}
		this.entityManager.merge(satiator);
		satiator.setModificationDate(new Date());
		return maintainer.getSubmoduleDependencies();
	}

}
