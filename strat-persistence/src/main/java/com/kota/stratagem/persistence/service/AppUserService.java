package com.kota.stratagem.persistence.service;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;

@Local
public interface AppUserService {

	AppUser create(String name, String passwordHash, String email, Role role);

	AppUser readElementary(Long id);

	AppUser readElementary(String username);

	AppUser readWithNotifications(Long id);

	AppUser readWithNotifications(String username);

	AppUser readWithSupervisedTeams(Long id);

	AppUser readWithSupervisedTeams(String username);

	AppUser readComplete(Long id);

	AppUser readComplete(String username);

	Set<AppUser> readByRole(Role role);

	Set<AppUser> readAll();

	AppUser update(Long id, String name, String passwordHash, String email, Role role, String modifier);

	AppUser updateNotificationViewCount(Long id, int notificationViewCount);

	AppUser updateImageSelector(Long id, int imageSelector);

	void delete(Long id) throws CoherentPersistenceServiceException;

	boolean exists(Long id);

	void addNotification(Long id, Notification notification);

}
