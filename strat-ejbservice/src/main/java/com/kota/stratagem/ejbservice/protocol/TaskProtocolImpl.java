package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ImpedimentService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless(mappedName = "ejb/taskProtocol")
public class TaskProtocolImpl implements TaskProtocol {

	private static final Logger LOGGER = Logger.getLogger(TaskProtocolImpl.class);

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectSerivce;

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ImpedimentService impedimentService;

	@EJB
	private TaskConverter converter;

	@Override
	public TaskRepresentor getTask(Long id) throws AdaptorException {
		try {
			final TaskRepresentor representor = this.converter.to(this.taskService.read(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Task (id: " + id + ") --> " + representor);
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<TaskRepresentor> getAllTasks() {
		Set<TaskRepresentor> representors = new HashSet<>();
		try {
			representors = this.converter.to(this.taskService.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all tasks --> " + representors.size() + " item(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
		}
		return new ArrayList<TaskRepresentor>(representors);
	}

	@Override
	public TaskRepresentor saveTask(Long id, String name, String description, int priority, double completion, Date deadline, String operator,
			Set<TeamRepresentor> assignedTeams, Set<AppUserRepresentor> assignedUsers, Set<ImpedimentRepresentor> impediments,
			Set<TaskRepresentor> dependantTasks, Set<TaskRepresentor> taskDependencies, Long objective, Long project) throws AdaptorException {
		try {
			Task task = null;
			if ((id != null) && this.taskService.exists(id)) {
				final Set<Team> teams = new HashSet<Team>();
				final Set<AppUser> users = new HashSet<AppUser>();
				final Set<Impediment> taskImpediments = new HashSet<Impediment>();
				final Set<Task> dependants = new HashSet<Task>();
				final Set<Task> dependencies = new HashSet<Task>();
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
				if (impediments != null) {
					for (final ImpedimentRepresentor impediment : impediments) {
						taskImpediments.add(this.impedimentService.read(impediment.getId()));
					}
				}
				if (dependantTasks != null) {
					for (final TaskRepresentor taskRepresentor : dependantTasks) {
						dependants.add(this.taskService.read(taskRepresentor.getId()));
					}
				}
				if (taskDependencies != null) {
					for (final TaskRepresentor taskRepresentor : taskDependencies) {
						dependants.add(this.taskService.read(taskRepresentor.getId()));
					}
				}
				task = this.taskService.update(id, name, description, priority, completion, deadline, this.appUserService.read(operator), teams, users,
						taskImpediments, dependants, dependencies, objective, project);
			} else {
				task = this.taskService.create(name, description, priority, completion, deadline, this.appUserService.read(operator), null, null, null, null,
						null, objective, project);
			}
			return this.converter.to(task);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeTask(Long id) throws AdaptorException {
		try {
			this.taskService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}