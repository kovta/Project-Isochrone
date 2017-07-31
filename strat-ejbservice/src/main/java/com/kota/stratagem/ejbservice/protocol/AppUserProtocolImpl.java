package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.AppUserConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ImpedimentService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;
import com.kota.stratagem.security.encryption.PasswordGenerationService;

@Stateless(mappedName = "ejb/appUserProtocol")
public class AppUserProtocolImpl implements AppUserProtocol {

	private static final Logger LOGGER = Logger.getLogger(AppUserProtocolImpl.class);

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private TaskService taskService;

	@EJB
	private ImpedimentService impedimnetService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserConverter converter;

	@EJB
	private PasswordGenerationService passwordGenerator;

	@EJB
	private SessionContextAccessor sessionContextAccessor;

	@Override
	public AppUserRepresentor getAppUser(Long id) throws AdaptorException {
		try {
			final AppUserRepresentor representor = this.converter.to(this.appUserService.read(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get AppUser (id: " + id + ") --> " + representor);
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public AppUserRepresentor getAppUser(String username) throws AdaptorException {
		try {
			final AppUserRepresentor representor = this.converter.to(this.appUserService.read(username));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get AppUser (username: " + username + ") --> " + representor);
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<AppUserRepresentor> getAllAppUsers() throws AdaptorException {
		Set<AppUserRepresentor> representors = new HashSet<AppUserRepresentor>();
		try {
			representors = this.converter.to(this.appUserService.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all AppUsers : " + representors.size() + " users(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
		}
		return new ArrayList<AppUserRepresentor>(representors);
	}

	@Override
	public List<List<AppUserRepresentor>> getAssignableAppUserClusters(ObjectiveRepresentor objective) throws AdaptorException {
		final List<List<AppUserRepresentor>> clusters = new ArrayList<>();
		final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		final List<AppUserObjectiveAssignmentRepresentor> assignments = objective.getAssignedUsers();
		try {
			for (final RoleRepresentor subordinateRole : RoleRepresentor.valueOf(this.appUserService.read(operator).getRole().toString())
					.getSubordinateRoles()) {
				final List<AppUserRepresentor> userList = new ArrayList<>(
						this.converter.to(this.appUserService.readByRole(Role.valueOf(subordinateRole.getName()))));
				for (final AppUserObjectiveAssignmentRepresentor assignment : assignments) {
					if (userList.contains(assignment.getRecipient())) {
						userList.remove(assignment.getRecipient());
					}
				}
				if (userList.size() > 0) {
					clusters.add(userList);
				}
			}
			for (final List<AppUserRepresentor> cluster : clusters) {
				Collections.sort(cluster, new Comparator<AppUserRepresentor>() {
					@Override
					public int compare(AppUserRepresentor obj_a, AppUserRepresentor obj_b) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
				});
			}
			final List<AppUserRepresentor> self = new ArrayList<>();
			self.add(this.converter.to(this.appUserService.read(operator)));
			for (final AppUserObjectiveAssignmentRepresentor assignment : assignments) {
				if (self.contains(assignment.getRecipient())) {
					self.remove(assignment.getRecipient());
				}
			}
			if (self.size() > 0) {
				clusters.add(self);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all AppUsers assignable by : " + operator + " | " + clusters.size() + " users(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
		}
		return clusters;
	}

	@Override
	public AppUserRepresentor saveAppUser(Long id, String name, String password, String email, RoleRepresentor role, AppUserRepresentor operator,
			Set<ObjectiveRepresentor> objectives, Set<ProjectRepresentor> projects, Set<SubmoduleRepresentor> submodules, Set<TaskRepresentor> tasks,
			Set<ImpedimentRepresentor> reportedImpediments, Set<ImpedimentRepresentor> processedImpediments, Set<TeamRepresentor> supervisedTeams,
			Set<TeamRepresentor> teamMemberships) throws AdaptorException {
		try {
			AppUser user = null;
			final Role userRole = Role.valueOf(role.getName());
			if ((id != null) && this.appUserService.exists(id)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update AppUser (id: " + id + ")");
				}
				final Set<Objective> userObjectives = new HashSet<Objective>();
				final Set<Project> userProjects = new HashSet<Project>();
				final Set<Submodule> userSubmodules = new HashSet<Submodule>();
				final Set<Task> userTasks = new HashSet<Task>();
				final Set<Impediment> impedimentsReported = new HashSet<Impediment>();
				final Set<Impediment> impedimentsProcessed = new HashSet<Impediment>();
				final Set<Team> teamsSupervised = new HashSet<Team>();
				final Set<Team> memberships = new HashSet<Team>();
				for (final ObjectiveRepresentor objective : objectives) {
					userObjectives.add(this.objectiveService.readElementary(objective.getId()));
				}
				for (final ProjectRepresentor project : projects) {
					userProjects.add(this.projectService.readElementary(project.getId()));
				}
				for (final SubmoduleRepresentor submodule : submodules) {
					userSubmodules.add(this.submoduleService.readElementary(submodule.getId()));
				}
				for (final TaskRepresentor task : tasks) {
					userTasks.add(this.taskService.read(task.getId()));
				}
				for (final ImpedimentRepresentor impediment : reportedImpediments) {
					impedimentsReported.add(this.impedimnetService.read(impediment.getId()));
				}
				for (final ImpedimentRepresentor impediment : processedImpediments) {
					impedimentsProcessed.add(this.impedimnetService.read(impediment.getId()));
				}
				for (final TeamRepresentor team : supervisedTeams) {
					teamsSupervised.add(this.teamService.read(team.getId()));
				}
				for (final TeamRepresentor team : teamMemberships) {
					memberships.add(this.teamService.read(team.getId()));
				}
				// Submodules not passed although link addition method still needs further specification
				user = this.appUserService.update(id, name, password, email, userRole, operator != null ? this.appUserService.read(operator.getId()) : null,
						userObjectives, userProjects, userSubmodules, userTasks, impedimentsReported, impedimentsProcessed, teamsSupervised, memberships);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create AppUser (name: " + name + ")");
				}
				user = this.appUserService.create(name, this.passwordGenerator.GenerateBCryptPassword(password), email, userRole,
						operator != null ? this.appUserService.read(operator.getId()) : null, null, null, null, null, null, null, null, null);
			}
			return this.converter.to(user);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeAppUser(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove AppUser (id: " + id + ")");
			}
			this.appUserService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}
