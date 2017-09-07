package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.AppUserConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AbstractAppUserAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserTaskAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.NotificationRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.security.encryption.PasswordGenerationService;

@Regulated
@Stateless(mappedName = "ejb/appUserProtocol")
public class AppUserProtocolImpl implements AppUserProtocol {

	@EJB
	private AppUserService appUserService;

	@Inject
	private AppUserConverter converter;

	@EJB
	private PasswordGenerationService passwordGenerator;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

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
		for (final List<AppUserRepresentor> cluster : clusters) {
			Collections.sort(cluster, new Comparator<AppUserRepresentor>() {
				@Override
				public int compare(AppUserRepresentor obj_a, AppUserRepresentor obj_b) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
			});
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
		return clusters;
	}

	private AppUserRepresentor sortUserCollections(AppUserRepresentor representor) {
		Collections.sort(representor.getNotifications(), new Comparator<NotificationRepresentor>() {
			@Override
			public int compare(NotificationRepresentor obj_a, NotificationRepresentor obj_b) {
				return obj_b.getCreationDate().compareTo(obj_a.getCreationDate());
			}
		});
		Collections.sort(representor.getObjectives(), new Comparator<AppUserObjectiveAssignmentRepresentor>() {
			@Override
			public int compare(AppUserObjectiveAssignmentRepresentor obj_a, AppUserObjectiveAssignmentRepresentor obj_b) {
				return obj_b.getCreationDate().compareTo(obj_a.getCreationDate());
			}
		});
		Collections.sort(representor.getProjects(), new Comparator<AppUserProjectAssignmentRepresentor>() {
			@Override
			public int compare(AppUserProjectAssignmentRepresentor obj_a, AppUserProjectAssignmentRepresentor obj_b) {
				return obj_b.getCreationDate().compareTo(obj_a.getCreationDate());
			}
		});
		Collections.sort(representor.getSubmodules(), new Comparator<AppUserSubmoduleAssignmentRepresentor>() {
			@Override
			public int compare(AppUserSubmoduleAssignmentRepresentor obj_a, AppUserSubmoduleAssignmentRepresentor obj_b) {
				return obj_b.getCreationDate().compareTo(obj_a.getCreationDate());
			}
		});
		Collections.sort(representor.getTasks(), new Comparator<AppUserTaskAssignmentRepresentor>() {
			@Override
			public int compare(AppUserTaskAssignmentRepresentor obj_a, AppUserTaskAssignmentRepresentor obj_b) {
				return obj_b.getCreationDate().compareTo(obj_a.getCreationDate());
			}
		});
		return representor;
	}

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
	public AppUserRepresentor getAppUser(Long id) throws AdaptorException {
		final AppUserRepresentor representor = this.converter.toComplete(this.appUserService.readComplete(id));
		if (this.isOperatorAccount(representor) && (representor.getNotifications().size() != representor.getNotificationViewCount())) {
			this.equalizeViewedNotifications(representor);
		}
		return this.sortUserCollections(representor);
	}

	@Override
	public AppUserRepresentor getAppUser(String username) throws AdaptorException {
		final AppUserRepresentor representor = this.converter.toComplete(this.appUserService.readComplete(username));
		if (this.isOperatorAccount(representor) && (representor.getNotifications().size() != representor.getNotificationViewCount())) {
			this.equalizeViewedNotifications(representor);
		}
		return this.sortUserCollections(representor);
	}

	@Override
	public List<AppUserRepresentor> getAllAppUsers() throws AdaptorException {
		Set<AppUserRepresentor> representors = new HashSet<AppUserRepresentor>();
		representors = this.converter.toSubComplete(this.appUserService.readAll());
		final List<AppUserRepresentor> representorList = new ArrayList<AppUserRepresentor>(representors);
		Collections.sort(representorList, new Comparator<AppUserRepresentor>() {
			@Override
			public int compare(AppUserRepresentor obj_a, AppUserRepresentor obj_b) {
				return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
			}
		});
		return representorList;
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
	public AppUserRepresentor saveAppUser(Long id, String name, String password, String email, RoleRepresentor role, String operator) throws AdaptorException {
		return this.converter.toComplete(((id != null) && this.appUserService.exists(id))
				? this.appUserService.update(id, name, password != null ? password : null, email, Role.valueOf(role.getName()), operator)
				: this.appUserService.create(name, this.passwordGenerator.GenerateBCryptPassword(password), email, Role.valueOf(role.getName())));
	}

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
	public boolean isOperatorAccount(AppUserRepresentor operator) throws AdaptorException {
		return this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId() == operator.getId();
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
