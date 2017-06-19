package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Objective;
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
	private ProjectConverter converter;

	@Override
	public ProjectRepresentor getProject(Long id) throws AdaptorException {
		try {
			final ProjectRepresentor representor = this.converter.to(this.projectService.readWithTasks(id));
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
	public List<ProjectRepresentor> getAllProjects(final ProjectCriteria criteria) {
		Set<ProjectRepresentor> representors = new HashSet<ProjectRepresentor>();
		try {
			Set<Project> projects = null;
			if (criteria.getStatus() == null) {
				projects = this.projectService.readAll();
			} else {
				projects = this.projectService.readByStatus(ProjectStatus.valueOf(criteria.getStatus().name()));
			}
			representors = this.converter.to(projects);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all projects by criteria (" + criteria + ") --> " + representors.size() + " projects(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
		}
		final List<ProjectRepresentor> projects = new ArrayList<ProjectRepresentor>(representors);
		return projects;
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
						projectTasks, teams, users, projectImpediments, this.objectiveService.readWithProjectsAndTasks(objective));
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create Project (name: " + name + ")");
				}
				final Objective parentObjective = this.objectiveService.readWithProjectsAndTasks(objective);
				project = this.projectService.create(name, description, projectStatus, deadline, confidential, this.appUserService.read(operator), null, null,
						null, null, parentObjective);
				// objectiveService
			}
			return this.converter.to(project);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeProject(Long id) throws AdaptorException {
		try {
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