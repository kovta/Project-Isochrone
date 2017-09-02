package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.util.Constants;

@Stateless(mappedName = "ejb/projectProtocol")
public class ProjectProtocolImpl implements ProjectProtocol {

	private static final Logger LOGGER = Logger.getLogger(ProjectProtocolImpl.class);

	@EJB
	private ProjectService projectService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private ObjectiveConverter objectiveConverter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@EJB
	private LifecycleOverseer overseer;

	@Override
	public ProjectRepresentor getProject(Long id) throws AdaptorException {
		try {
			final ProjectRepresentor representor = this.projectConverter.toComplete(this.projectService.readComplete(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Project (id: " + id + ") --> " + representor);
			}
			Collections.sort(representor.getSubmodules(), new Comparator<SubmoduleRepresentor>() {
				@Override
				public int compare(SubmoduleRepresentor obj_a, SubmoduleRepresentor obj_b) {
					final int c = obj_a.getTasks().size() - obj_b.getTasks().size();
					if (c == 0) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
					return c * -1;
				}
			});
			Collections.sort(representor.getOverdueSubmodules(), new Comparator<SubmoduleRepresentor>() {
				@Override
				public int compare(SubmoduleRepresentor obj_a, SubmoduleRepresentor obj_b) {
					final int c = obj_a.getDeadline().compareTo(obj_b.getDeadline());
					if (c == 0) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
					return c;
				}
			});
			Collections.sort(representor.getOngoingSubmodules(), new Comparator<SubmoduleRepresentor>() {
				@Override
				public int compare(SubmoduleRepresentor obj_a, SubmoduleRepresentor obj_b) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
			});
			Collections.sort(representor.getCompletedSubmodules(), new Comparator<SubmoduleRepresentor>() {
				@Override
				public int compare(SubmoduleRepresentor obj_a, SubmoduleRepresentor obj_b) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
			});
			Collections.sort(representor.getOverdueTasks(), new Comparator<TaskRepresentor>() {
				@Override
				public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
					final int c = obj_a.getDeadline().compareTo(obj_b.getDeadline());
					if (c == 0) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
					return c;
				}
			});
			Collections.sort(representor.getOngoingTasks(), new Comparator<TaskRepresentor>() {
				@Override
				public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
			});
			Collections.sort(representor.getCompletedTasks(), new Comparator<TaskRepresentor>() {
				@Override
				public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
			});
			Collections.sort(representor.getAssignedUsers(), new Comparator<AppUserProjectAssignmentRepresentor>() {
				@Override
				public int compare(AppUserProjectAssignmentRepresentor obj_a, AppUserProjectAssignmentRepresentor obj_b) {
					return obj_a.getRecipient().getName().toLowerCase().compareTo(obj_b.getRecipient().getName().toLowerCase());
				}
			});
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<ProjectRepresentor> getAllProjects(final ProjectCriteria criteria) throws AdaptorException {
		try {
			Set<Project> projects = null;
			if (criteria.getStatus() == null) {
				projects = this.projectService.readAll();
			} else {
				projects = this.projectService.readByStatus(ProjectStatus.valueOf(criteria.getStatus().name()));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Projects by criteria (" + criteria + "): " + projects.size() + " Projects(s)");
			}
			return new ArrayList<ProjectRepresentor>(this.projectConverter.toSimplified(projects));
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<ObjectiveRepresentor> getObjectiveProjectClusters() throws ServiceException {
		try {
			final List<ObjectiveRepresentor> clusters = new ArrayList<>();
			final List<ProjectRepresentor> projects = new ArrayList<ProjectRepresentor>(this.projectConverter.toSimplified(this.projectService.readAll()));
			for (final ProjectRepresentor project : projects) {
				boolean contains = false;
				for (final ObjectiveRepresentor cluster : clusters) {
					if (cluster.getId() == project.getObjective().getId()) {
						contains = true;
					}
				}
				if (!contains) {
					clusters.add(this.objectiveConverter.toSimplified(this.objectiveService.readWithProjectsAndTasks(project.getObjective().getId())));
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all projects grouped by parent objectives: " + clusters.size() + " cluster(s) | " + projects.size() + " project(s)");
			}
			Collections.sort(clusters, new Comparator<ObjectiveRepresentor>() {
				@Override
				public int compare(ObjectiveRepresentor obj_a, ObjectiveRepresentor obj_b) {
					final int c = obj_a.getPriority() - obj_b.getPriority();
					if (c == 0) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
					return c;
				}
			});
			for (final ObjectiveRepresentor objective : clusters) {
				final List<ProjectRepresentor> expandedProjects = new ArrayList<>();
				for (final ProjectRepresentor project : objective.getProjects()) {
					expandedProjects.add(this.projectConverter.toSimplified(this.projectService.readWithSubmodulesAndTasks(project.getId())));
				}
				objective.setProjects(expandedProjects);
				Collections.sort(objective.getProjects(), new Comparator<ProjectRepresentor>() {
					@Override
					public int compare(ProjectRepresentor obj_a, ProjectRepresentor obj_b) {
						final int c1 = obj_a.getSubmodules().size() - obj_b.getSubmodules().size();
						if (c1 == 0) {
							final int c2 = obj_a.getTasks().size() - obj_b.getTasks().size();
							if (c2 == 0) {
								return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
							}
							return c2 * -1;
						}
						return c1 * -1;
					}
				});
			}
			return clusters;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public ProjectRepresentor saveProject(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			String operator, Long objective) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(id != null ? "Update Project (id: " + id + ")" : "Create Project (" + name + ")");
			}
			final ProjectStatus projectStatus = ProjectStatus.valueOf(status.name());
			ProjectRepresentor origin = null;
			if (id != null) {
				origin = this.projectConverter.toElementary(this.projectService.readElementary(id));
			}
			final ProjectRepresentor representor = this.projectConverter.toComplete((id != null) && this.projectService.exists(id)
					? this.projectService.update(id, name, description, projectStatus, deadline, confidential, operator)
					: this.projectService.create(name, description, projectStatus, deadline, confidential, this.appUserService.readElementary(operator).getId(),
							objective));
			if (id != null) {
				this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
			} else {
				this.overseer.created(representor.toTextMessage());
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeProject(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove Project (id: " + id + ")");
			}
			this.overseer.deleted(this.projectConverter.toElementary(this.projectService.readElementary(id)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
					+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName());
			this.projectService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}