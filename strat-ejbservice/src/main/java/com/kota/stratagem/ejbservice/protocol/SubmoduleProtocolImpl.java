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
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless(mappedName = "ejb/submoduleProtocol")
public class SubmoduleProtocolImpl implements SubmoduleProtocol {

	private static final Logger LOGGER = Logger.getLogger(SubmoduleProtocolImpl.class);

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleSerive;

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private SubmoduleConverter submoduleConverter;

	@EJB
	private ProjectConverter projectConverter;

	@Override
	public SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException {
		try {
			final SubmoduleRepresentor representor = this.submoduleConverter.to(this.submoduleSerive.readWithTasks(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Submodule (id: " + id + ") --> " + representor);
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<SubmoduleRepresentor> getAllSubmodules() throws AdaptorException {
		Set<SubmoduleRepresentor> representors = new HashSet<>();
		try {
			representors = this.submoduleConverter.to(this.submoduleSerive.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Submodules --> " + representors.size() + " item(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
		return new ArrayList<SubmoduleRepresentor>(representors);
	}

	@Override
	public SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Set<TaskRepresentor> tasks,
			Set<TeamRepresentor> assignedTeams, Set<AppUserRepresentor> assignedUsers, Long project) throws AdaptorException {
		try {
			Submodule submodule = null;
			if ((id != null) && this.submoduleSerive.exists(id)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update Submodule (id: " + id + ")");
				}
				final Set<Task> submoduleTasks = new HashSet<Task>();
				final Set<Team> teams = new HashSet<Team>();
				final Set<AppUser> users = new HashSet<AppUser>();
				if (tasks != null) {
					for (final TaskRepresentor task : tasks) {
						submoduleTasks.add(this.taskService.read(task.getId()));
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
				submodule = this.submoduleSerive.update(id, name, description, deadline, this.appUserService.read(operator), null, submoduleTasks, teams,
						users);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create Project (name: " + name + ")");
				}
				submodule = this.submoduleSerive.create(name, description, deadline, this.appUserService.read(operator), null, null, null, null, project);
			}
			return this.submoduleConverter.to(submodule);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeSubmodule(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove Submodule (id: " + id + ")");
			}
			this.submoduleSerive.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}
