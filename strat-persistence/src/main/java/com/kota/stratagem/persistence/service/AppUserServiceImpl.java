package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.AppUserParameter;
import com.kota.stratagem.persistence.query.AppUserQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Contained
@Stateless(mappedName = "ejb/appUserService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AppUserServiceImpl implements AppUserService {

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@Override
	public AppUser create(String name, String passwordHash, String email, Role role) {
		final AppUser user = new AppUser(name, passwordHash, email, role, new Date(), null, new Date(), 0, 0);
		this.entityManager.persist(user);
		this.entityManager.flush();
		return user;

	}

	@Override
	public AppUser readElementary(Long id) {
		return this.entityManager.createNamedQuery(AppUserQuery.GET_BY_ID, AppUser.class).setParameter(AppUserParameter.ID, id).getSingleResult();
	}

	@Override
	public AppUser readElementary(String username) {
		return this.entityManager.createNamedQuery(AppUserQuery.GET_BY_USERNAME, AppUser.class).setParameter(AppUserParameter.USERNAME, username)
				.getSingleResult();
	}

	@Override
	public AppUser readWithNotifications(Long id) {
		return this.entityManager.createNamedQuery(AppUserQuery.GET_BY_ID_WITH_NOTIFICATIONS, AppUser.class).setParameter(AppUserParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public AppUser readWithNotifications(String username) {
		return this.entityManager.createNamedQuery(AppUserQuery.GET_BY_USERNAME_WITH_NOTIFICATIONS, AppUser.class)
				.setParameter(AppUserParameter.USERNAME, username).getSingleResult();
	}

	@Override
	public AppUser readComplete(Long id) {
		return this.entityManager.createNamedQuery(AppUserQuery.GET_BY_ID_COMPLETE, AppUser.class).setParameter(AppUserParameter.ID, id).getSingleResult();
	}

	@Override
	public AppUser readComplete(String username) {
		return this.entityManager.createNamedQuery(AppUserQuery.GET_BY_USERNAME_COMPLETE, AppUser.class).setParameter(AppUserParameter.USERNAME, username)
				.getSingleResult();
	}

	@Override
	public Set<AppUser> readByRole(Role role) {
		return new HashSet<AppUser>(
				this.entityManager.createNamedQuery(AppUserQuery.GET_ALL_BY_ROLE, AppUser.class).setParameter(AppUserParameter.ROLE, role).getResultList());
	}

	@Override
	public Set<AppUser> readAll() {
		return new HashSet<AppUser>(this.entityManager.createNamedQuery(AppUserQuery.GET_ALL_APP_USERS, AppUser.class).getResultList());
	}

	@Override
	public AppUser update(Long id, String name, String passwordHash, String email, Role role, String modifier) {
		final AppUser user = this.readComplete(id);
		final AppUser operator = this.readElementary(modifier);
		user.setName(name);
		if (passwordHash != null) {
			user.setPasswordHash(passwordHash);
		}
		user.setEmail(email);
		user.setRole(role);
		if (user.getAccountModifier().getId() != operator.getId()) {
			user.setAccountModifier(operator);
		}
		user.setAcountModificationDate(new Date());
		return this.entityManager.merge(user);
	}

	@Override
	public AppUser updateNotificationViewCount(Long id, int notificationViewCount) {
		final AppUser user = this.readElementary(id);
		user.setNotificationViewCount(notificationViewCount);
		return this.entityManager.merge(user);
	}

	@Override
	public AppUser updateImageSelector(Long id, int imageSelector) {
		final AppUser user = this.readElementary(id);
		user.setImageSelector(imageSelector);
		return this.entityManager.merge(user);
	}

	@Override
	public void delete(Long id) throws CoherentPersistenceServiceException {
		if (this.exists(id)) {
			if (this.readComplete(id).getProjects().size() == 0) {
				this.entityManager.createNamedQuery(AppUserQuery.REMOVE_BY_ID).setParameter(AppUserParameter.ID, id).executeUpdate();
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "AppUser has undeleted dependency(s)", id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "AppUser doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) {
		return this.entityManager.createNamedQuery(AppUserQuery.COUNT_BY_ID, Long.class).setParameter(AppUserParameter.ID, id).getSingleResult() == 1;
	}

	@Override
	public void addNotification(Long id, Notification notification) {
		final AppUser recipient = this.entityManager.createNamedQuery(AppUserQuery.GET_BY_ID_WITH_NOTIFICATIONS, AppUser.class)
				.setParameter(AppUserParameter.ID, id).getSingleResult();
		recipient.addNotification(notification);
		this.entityManager.merge(recipient);
	}

}
