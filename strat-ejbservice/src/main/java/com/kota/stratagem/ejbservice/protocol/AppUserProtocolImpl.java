package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserNameComparator;
import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.AppUserConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.AppUserOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AbstractAppUserAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.security.encryption.PasswordGenerationService;
import com.kota.stratagem.security.util.Authorizations;

@PermitAll
@Regulated
@Stateless(mappedName = EJBServiceConfiguration.APP_USER_PROTOCOL_SIGNATURE)
public class AppUserProtocolImpl implements AppUserProtocol {

	@EJB
	private AppUserService appUserService;

	@Inject
	private AppUserConverter converter;

	@EJB
	private PasswordGenerationService passwordGenerator;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Inject
	@AppUserOriented
	private DTOExtensionManager extensionManager;

	@Override
	public int getAppUserNewNotificationCount(String username) throws AdaptorException {
		final AppUserRepresentor representor = this.converter.toSimplified(this.appUserService.readWithNotifications(username));
		return (representor.getNotifications().size() - representor.getNotificationViewCount());
	}

	@Override
	public int getAppUserImageSelector(String username) throws AdaptorException {
		return this.converter.toElementary(this.appUserService.readElementary(username)).getImageSelector();
	}

	@Override
	public AppUserRepresentor getOperator() throws AdaptorException {
		return this.getAppUser(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName());
	}

	@Override
	public AppUserRepresentor getAppUser(Long id) throws AdaptorException {
		final AppUserRepresentor representor = this.converter.toComplete(this.appUserService.readComplete(id));
		if (this.isOperatorAccount(representor) && (representor.getNotifications().size() != representor.getNotificationViewCount())) {
			this.equalizeViewedNotifications(representor);
		}
		return (AppUserRepresentor) this.extensionManager.prepare(representor);
	}

	@Override
	public AppUserRepresentor getAppUser(String username) throws AdaptorException {
		final AppUserRepresentor representor = this.converter.toComplete(this.appUserService.readComplete(username));
		if (this.isOperatorAccount(representor) && (representor.getNotifications().size() != representor.getNotificationViewCount())) {
			this.equalizeViewedNotifications(representor);
		}
		return (AppUserRepresentor) this.extensionManager.prepare(representor);
	}

	@Override
	public List<AppUserRepresentor> getAllAppUsers() throws AdaptorException {
		return (List<AppUserRepresentor>) this.extensionManager.prepareBatch(new ArrayList<AppUserRepresentor>(this.converter.toSubComplete(this.appUserService.readAll())));
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

	private <T extends AbstractAppUserAssignmentRepresentor> List<List<AppUserRepresentor>> retrieveObjectRelatedClusterList(List<T> assignments) {
		final List<List<AppUserRepresentor>> clusters = new ArrayList<>();
		final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		for (final RoleRepresentor subordinateRole : RoleRepresentor.valueOf(this.appUserService.readElementary(operator).getRole().toString())
				.getSubordinateRoles()) {
			final List<AppUserRepresentor> userList = new ArrayList<>(
					this.converter.toElementary(this.appUserService.readByRole(Role.valueOf(subordinateRole.getName()))));
			for (final T assignment : assignments) {
				if (userList.contains(assignment.getRecipient())) {
					userList.remove(assignment.getRecipient());
				}
			}
			if (userList.size() > 0) {
				clusters.add(userList);
			}
		}
		final List<AppUserRepresentor> self = new ArrayList<>();
		self.add(this.converter.toElementary(this.appUserService.readElementary(operator)));
		for (final T assignment : assignments) {
			if (self.contains(assignment.getRecipient())) {
				self.remove(assignment.getRecipient());
			}
		}
		if (self.size() > 0) {
			clusters.add(self);
		}
		for (final List<AppUserRepresentor> cluster : clusters) {
			Collections.sort(cluster, new AppUserNameComparator());
		}
		return clusters;
	}

	@Override
	public List<List<AppUserRepresentor>> getAssignableAppUserClusters(TeamRepresentor team) throws AdaptorException {
		final List<List<AppUserRepresentor>> clusters = new ArrayList<>();
		final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		for (final RoleRepresentor subordinateRole : RoleRepresentor.valueOf(this.appUserService.readElementary(operator).getRole().toString())
				.getSubordinateRoles()) {
			final List<AppUserRepresentor> userList = new ArrayList<>(
					this.converter.toElementary(this.appUserService.readByRole(Role.valueOf(subordinateRole.getName()))));
			for (final AppUserRepresentor user : team.getMembers()) {
				if (userList.contains(user) || user.equals(team.getLeader())) {
					userList.remove(user);
				}
			}
			if (userList.size() > 0) {
				clusters.add(userList);
			}
		}
		final List<AppUserRepresentor> self = new ArrayList<>();
		self.add(this.converter.toElementary(this.appUserService.readElementary(operator)));
		for (final AppUserRepresentor user : team.getMembers()) {
			if (self.contains(user) || user.equals(team.getLeader())) {
				self.remove(user);
			}
		}
		if (self.size() > 0) {
			clusters.add(self);
		}
		for (final List<AppUserRepresentor> cluster : clusters) {
			Collections.sort(cluster, new AppUserNameComparator());
		}
		return clusters;
	}

	@Override
	public List<AppUserRepresentor> getAssignableAppUsers() throws AdaptorException {
		final List<AppUserRepresentor> users = new ArrayList<>();
		final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		for (final RoleRepresentor subordinateRole : RoleRepresentor.valueOf(this.appUserService.readElementary(operator).getRole().toString())
				.getSubordinateRoles()) {
			for (final AppUserRepresentor candidate : this.converter.toElementary(this.appUserService.readByRole(Role.valueOf(subordinateRole.getName())))) {
				users.add(candidate);
			}
			users.add(this.converter.toElementary(this.appUserService.readElementary(operator)));
		}
		Collections.sort(users, new AppUserNameComparator());
		return users;
	}

	@Override
	public AppUserRepresentor saveAppUser(Long id, String name, String password, String email, RoleRepresentor role, String operator) throws AdaptorException {
		return (AppUserRepresentor) this.extensionManager.prepare(this.converter.toComplete(((id != null) && this.appUserService.exists(id))
				? this.appUserService.update(id, name, password != null ? password : null, email, Role.valueOf(role.getName()), operator)
				: this.appUserService.create(name, this.passwordGenerator.GenerateBCryptPassword(password), email, Role.valueOf(role.getName()))));
	}

	@RolesAllowed({Authorizations.CENTRAL_MANAGER_AUTHORIZATION_TITLE, Authorizations.DEPARTMENT_MANAGER_AUTHORIZATION_TITLE})
	@Override
	public void removeAppUser(Long id) throws AdaptorException {
		try {
			this.appUserService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

	@Override
	public boolean isOperatorAccount(AppUserRepresentor appUser) throws AdaptorException {
		return this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId() == appUser.getId();
	}

	@Override
	public boolean isSubordinateUser(AppUserRepresentor appUser) throws AdaptorException {
		boolean subordinate = false;
		for (final RoleRepresentor subordinateRole : RoleRepresentor
				.valueOf(
						this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getRole().toString())
				.getSubordinateRoles()) {
			if (appUser.getRole().getTitle().equals(subordinateRole.getTitle())) {
				subordinate = true;
			}
		}
		return subordinate;
	}

	@Override
	public void equalizeViewedNotifications(AppUserRepresentor operator) throws AdaptorException {
		this.appUserService.updateNotificationViewCount(operator.getId(), operator.getNotifications().size());
	}

	@Override
	public void saveImageSelector(int imageSelector) throws AdaptorException {
		this.appUserService.updateImageSelector(
				this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), imageSelector);
	}

}
