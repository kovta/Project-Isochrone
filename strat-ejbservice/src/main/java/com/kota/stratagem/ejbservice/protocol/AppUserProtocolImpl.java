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
import com.kota.stratagem.ejbserviceclient.domain.AbstractAppUserAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
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

	private <T extends AbstractAppUserAssignmentRepresentor> List<List<AppUserRepresentor>> retrieveObjectRelatedClusterList(List<T> assignments) {
		final List<List<AppUserRepresentor>> clusters = new ArrayList<>();
		final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		try {
			for (final RoleRepresentor subordinateRole : RoleRepresentor.valueOf(this.appUserService.read(operator).getRole().toString())
					.getSubordinateRoles()) {
				final List<AppUserRepresentor> userList = new ArrayList<>(
						this.converter.to(this.appUserService.readByRole(Role.valueOf(subordinateRole.getName()))));
				for (final T assignment : assignments) {
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
			for (final T assignment : assignments) {
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
		return this.retrieveObjectRelatedClusterList(objective.getAssignedUsers());
	}

	@Override
	public List<List<AppUserRepresentor>> getAssignableAppUserClusters(ProjectRepresentor project) throws AdaptorException {
		return this.retrieveObjectRelatedClusterList(project.getAssignedUsers());
	}

	@Override
	public List<List<AppUserRepresentor>> getAssignableAppUserClusters(SubmoduleRepresentor submodule) throws AdaptorException {
		return this.retrieveObjectRelatedClusterList(submodule.getAssignedUsers());
	}

	@Override
	public List<List<AppUserRepresentor>> getAssignableAppUserClusters(TaskRepresentor task) throws AdaptorException {
		return this.retrieveObjectRelatedClusterList(task.getAssignedUsers());
	}

	@Override
	public AppUserRepresentor saveAppUser(Long id, String name, String password, String email, RoleRepresentor role, AppUserRepresentor operator)
			throws AdaptorException {
		try {

			AppUser user = null;
			final Role userRole = Role.valueOf(role.getName());
			if ((id != null) && this.appUserService.exists(id)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update AppUser (id: " + id + ")");
				}
				user = this.appUserService.update(id, name, password, email, userRole, operator != null ? this.appUserService.read(operator.getId()) : null);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create AppUser (name: " + name + ")");
				}
				user = this.appUserService.create(name, this.passwordGenerator.GenerateBCryptPassword(password), email, userRole,
						operator != null ? this.appUserService.read(operator.getId()) : null);
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
