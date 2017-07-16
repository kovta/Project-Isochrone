package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ImpedimentService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless(mappedName = "ejb/projectProtocol")
public class ProjectProtocolImpl implements ProjectProtocol {

	private static final Logger LOGGER = Logger.getLogger(ProjectProtocolImpl.class);

	@EJB
	private ProjectService projectService;

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ImpedimentService impedimentService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private ObjectiveConverter objectiveConverter;

	@Override
	public ProjectRepresentor getProject(Long id) throws AdaptorException {
		try {
			final ProjectRepresentor representor = this.projectConverter.to(this.projectService.readWithTasks(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Project (id: " + id + ") --> " + representor);
			}
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
				LOGGER.debug("Fetch all projects by criteria (" + criteria + "): " + projects.size() + " projects(s)");
			}
			return new ArrayList<ProjectRepresentor>(this.projectConverter.to(projects));
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<ObjectiveRepresentor> getObjectiveProjectClusters() throws ServiceException {
		try {
			final List<ObjectiveRepresentor> clusters = new ArrayList<>();
			final List<ProjectRepresentor> projects = new ArrayList<ProjectRepresentor>(this.projectConverter.to(this.projectService.readAll()));
			for (final ProjectRepresentor project : projects) {
				if (!clusters.contains(project.getObjective())) {
					clusters.add(this.objectiveConverter.to(this.objectiveService.readWithProjectsAndTasks(project.getObjective().getId())));
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
						return obj_a.getName().compareTo(obj_b.getName());
					}
					return c;
				}
			});
			for (final ObjectiveRepresentor objective : clusters) {
				Collections.sort(objective.getProjects(), new Comparator<ProjectRepresentor>() {
					@Override
					public int compare(ProjectRepresentor obj_a, ProjectRepresentor obj_b) {
						return obj_a.getName().compareTo(obj_b.getName());
					}
				});
				final List<ProjectRepresentor> expandedProjects = new ArrayList<>();
				for (final ProjectRepresentor project : objective.getProjects()) {
					expandedProjects.add(this.projectConverter.to(this.projectService.readWithTasks(project.getId())));
				}
				objective.setProjects(expandedProjects);
			}
			return clusters;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public ProjectRepresentor saveProject(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			String operator, Set<TaskRepresentor> tasks, Set<TeamRepresentor> assignedTeams, Set<AppUserRepresentor> assignedUsers,
			Set<ImpedimentRepresentor> impediments, Long objective) throws AdaptorException {
		try {
			Project project = null;
			final ProjectStatus projectStatus = ProjectStatus.valueOf(status.name());
			if ((id != null) && this.projectService.exists(id)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update Project (id: " + id + ")");
				}
				final Set<Task> projectTasks = new HashSet<Task>();
				final Set<Team> teams = new HashSet<Team>();
				final Set<AppUser> users = new HashSet<AppUser>();
				final Set<Impediment> projectImpediments = new HashSet<Impediment>();
				for (final TaskRepresentor task : tasks) {
					projectTasks.add(this.taskService.read(task.getId()));
				}
				for (final TeamRepresentor team : assignedTeams) {
					teams.add(this.teamService.read(team.getId()));
				}
				for (final AppUserRepresentor user : assignedUsers) {
					users.add(this.appUserService.read(user.getId()));
				}
				for (final ImpedimentRepresentor impediment : impediments) {
					projectImpediments.add(this.impedimentService.read(impediment.getId()));
				}
				project = this.projectService.update(id, name, description, projectStatus, deadline, confidential, this.appUserService.read(operator),
						projectTasks, teams, users, projectImpediments, objective);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create Project (name: " + name + ")");
				}
				project = this.projectService.create(name, description, projectStatus, deadline, confidential, this.appUserService.read(operator), null, null,
						null, null, objective);
			}
			return this.projectConverter.to(project);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeProject(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove project (id: " + id + ")");
			}
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