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

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
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
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.util.Constants;

@Regulated
@Stateless(mappedName = "ejb/projectProtocol")
public class ProjectProtocolImpl implements ProjectProtocol {

	@EJB
	private ProjectService projectService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@Inject
	private ProjectConverter projectConverter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@EJB
	private LifecycleOverseer overseer;

	@Override
	public ProjectRepresentor getProject(Long id) throws AdaptorException {
		final ProjectRepresentor representor = this.projectConverter.toComplete(this.projectService.readComplete(id));
		// Collections.sort(representor.getSubmodules(), new Comparator<SubmoduleRepresentor>() {
		// @Override
		// public int compare(SubmoduleRepresentor obj_a, SubmoduleRepresentor obj_b) {
		// final int c = obj_a.getTasks().size() - obj_b.getTasks().size();
		// if (c == 0) {
		// return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
		// }
		// return c * -1;
		// }
		// });
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
	}

	@Override
	public ProjectRepresentor saveProject(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			String operator, Long objective) throws AdaptorException {
		final ProjectStatus projectStatus = ProjectStatus.valueOf(status.name());
		ProjectRepresentor origin = null;
		if (id != null) {
			origin = this.projectConverter.toDispatchable(this.projectService.readWithMonitoring(id));
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
	}

	@Override
	public void removeProject(Long id) throws AdaptorException {
		try {
			final String message = this.projectConverter.toDispatchable(this.projectService.readWithMonitoring(id)).toTextMessage()
					+ Constants.PAYLOAD_SEPARATOR + this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
			this.projectService.delete(id);
			this.overseer.deleted(message);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

}