package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.ProjectOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.security.domain.RestrictionLevel;
import com.kota.stratagem.security.interceptor.Authorized;

@Regulated
@Stateless(mappedName = EJBServiceConfiguration.PROJECT_PROTOCOL_SIGNATURE)
public class ProjectProtocolImpl implements ProjectProtocol {

	@EJB
	private ProjectService projectService;

	@Inject
	private ProjectConverter projectConverter;

	@Inject
	@ProjectOriented
	private DTOExtensionManager extensionManager;

	@Override
	public ProjectRepresentor getProject(Long id) throws AdaptorException {
		return (ProjectRepresentor) this.extensionManager.prepare(this.projectConverter.toComplete(this.projectService.readComplete(id)));
	}

	@Override
	public List<ProjectRepresentor> getAllProjects(final ProjectCriteria criteria) throws AdaptorException {
		Set<Project> projects = null;
		if (criteria.getStatus() == null) {
			projects = this.projectService.readAll();
		} else {
			projects = this.projectService.readByStatus(ProjectStatus.valueOf(criteria.getStatus().name()));
		}
		return new ArrayList<ProjectRepresentor>(this.projectConverter.toSimplified(projects));
	}

	@Override
	public List<ObjectiveRepresentor> getObjectiveProjectClusters() throws ServiceException {
		final List<ObjectiveRepresentor> clusters = new ArrayList<>();
		final List<ProjectRepresentor> projects = new ArrayList<ProjectRepresentor>(this.projectConverter.toSimplified(this.projectService.readAll()));
		for (final ProjectRepresentor project : projects) {
			boolean contains = false;
			for (final ObjectiveRepresentor cluster : clusters) {
				if (cluster.getId() == project.getObjective().getId()) {
					contains = true;
					cluster.addProject(project);
				}
			}
			if (!contains) {
				final ObjectiveRepresentor objective = project.getObjective();
				objective.addProject(project);
				clusters.add(objective);
			}
		}
		return (List<ObjectiveRepresentor>) this.extensionManager.prepareBatch(clusters);
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public ProjectRepresentor saveProject(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			String operator, Long objective) throws AdaptorException {
		return (ProjectRepresentor) this.extensionManager.prepare(this.projectConverter.toComplete((id != null) && this.projectService.exists(id)
				? this.projectService.update(id, name, description, ProjectStatus.valueOf(status.name()), deadline, confidential, operator)
				: this.projectService.create(name, description, ProjectStatus.valueOf(status.name()), deadline, confidential, operator, objective)));
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void removeProject(Long id) throws AdaptorException {
		try {
			this.projectService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

}