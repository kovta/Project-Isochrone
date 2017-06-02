package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.ObjectiveProtocolRemote;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless(mappedName = "ejb/objectiveProtocol")
public class ObjectiveProtocolImpl implements ObjectiveProtocol, ObjectiveProtocolRemote {

	private static final Logger LOGGER = Logger.getLogger(ObjectiveProtocolImpl.class);

	@EJB
	private ProjectService projectService;

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ObjectiveConverter converter;

	@Override
	public ObjectiveRepresentor getObjective(Long id) throws ServiceException {
		try {
			final ObjectiveRepresentor representor = this.converter.to(this.objectiveService.readWithProjectsAndTasks(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Objective (id: " + id + ") --> " + representor);
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<ObjectiveRepresentor> getAllObjectives() throws AdaptorException {
		Set<ObjectiveRepresentor> representors = new HashSet<ObjectiveRepresentor>();
		try {
			representors = this.converter.to(this.objectiveService.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Objectives : " + representors.size() + " objective(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
		}
		return new ArrayList<ObjectiveRepresentor>(representors);
	}

	@Override
	public ObjectiveRepresentor saveObjective(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status,
			Set<ProjectRepresentor> projects, Set<TaskRepresentor> tasks, Set<TeamRepresentor> assignedTeams, Set<AppUserRepresentor> assignedUsers)
			throws AdaptorException {
		try {
			Objective objective = null;
			final ObjectiveStatus objectiveStatus = ObjectiveStatus.valueOf(status.name());
			if ((id != null) && this.objectiveService.exists(id)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update Objective (id: " + id + ")");
				}
				final Set<Project> objectiveProjects = new HashSet<Project>();
				final Set<Task> objectiveTasks = new HashSet<Task>();
				final Set<Team> teams = new HashSet<Team>();
				final Set<AppUser> users = new HashSet<AppUser>();
				if (projects != null) {
					for (final ProjectRepresentor project : projects) {
						objectiveProjects.add(this.projectService.readElementary(project.getId()));
					}
				}
				if (tasks != null) {
					for (final TaskRepresentor task : tasks) {
						objectiveTasks.add(this.taskService.read(task.getId()));
					}
				}
				if (assignedTeams != null) {
					for (final TeamRepresentor team : assignedTeams) {
						teams.add(this.teamService.read(team.getId()));
					}
				}
				if (assignedUsers != null) {
					for (final AppUserRepresentor user : assignedUsers) {
						users.add(this.appUserService.read(user.getId()));
					}
				}
				objective = this.objectiveService.update(id, name, description, priority, objectiveStatus, objectiveProjects, objectiveTasks, teams, users);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create Objective");
				}
				objective = this.objectiveService.create(name, description, priority, objectiveStatus, null, null, null, null);
			}
			return this.converter.to(objective);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeObjective(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deleting Objective (id: " + id + ")");
			}
			this.objectiveService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}
