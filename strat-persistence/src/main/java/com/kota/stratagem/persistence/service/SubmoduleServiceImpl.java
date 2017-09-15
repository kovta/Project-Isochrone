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
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.SubmoduleParameter;
import com.kota.stratagem.persistence.query.SubmoduleQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Contained
@Stateless(mappedName = PersistenceServiceConfiguration.SUBMODULE_SERVICE_SIGNATURE)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SubmoduleServiceImpl implements SubmoduleService {

	@Inject
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ProjectService projectService;

	@Override
	public Submodule create(String name, String description, Date deadline, AppUser creator, Long project) {
		final Project parentProject = this.projectService.readElementary(project);
		final Submodule submodule = new Submodule(name, description, deadline, new Date(), new Date(), parentProject);
		AppUser operatorTemp;
		if (parentProject.getCreator().getId() == creator.getId()) {
			operatorTemp = parentProject.getCreator();
		} else {
			operatorTemp = this.appUserService.readElementary(creator.getId());
		}
		final AppUser operator = operatorTemp;
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
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_MONITORING, Submodule.class).setParameter(SubmoduleParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Submodule readWithAssignments(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_ASSIGNMENTS, Submodule.class).setParameter(SubmoduleParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Submodule readWithTasks(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_WITH_TASKS, Submodule.class).setParameter(SubmoduleParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Submodule readComplete(Long id) {
		return this.entityManager.createNamedQuery(SubmoduleQuery.GET_BY_ID_COMPLETE, Submodule.class).setParameter(SubmoduleParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Set<Submodule> readAll() {
		return new HashSet<Submodule>(this.entityManager.createNamedQuery(SubmoduleQuery.GET_ALL_SUBMODULES, Submodule.class).getResultList());
	}

	@Override
	public Submodule update(Long id, String name, String description, Date deadline, AppUser modifier) {
		final Submodule submodule = this.readComplete(id);
		final AppUser operator = this.appUserService.readElementary(modifier.getId());
		submodule.setName(name);
		submodule.setDescription(description);
		submodule.setDeadline(deadline);
		if (submodule.getCreator().getId() == operator.getId()) {
			submodule.setModifier(submodule.getCreator());
		} else if (submodule.getModifier().getId() != operator.getId()) {
			submodule.setModifier(operator);
		}
		submodule.setModificationDate(new Date());
		return this.entityManager.merge(submodule);
	}

	@Override
	public void delete(Long id) throws CoherentPersistenceServiceException {
		if (this.exists(id)) {
			if (this.readWithTasks(id).getTasks().size() == 0) {
				this.entityManager.createNamedQuery(SubmoduleQuery.REMOVE_BY_ID).setParameter(SubmoduleParameter.ID, id).executeUpdate();
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
		return this.entityManager.createNamedQuery(SubmoduleQuery.COUNT_BY_ID, Long.class).setParameter(SubmoduleParameter.ID, id).getSingleResult() > 0;
	}

}
