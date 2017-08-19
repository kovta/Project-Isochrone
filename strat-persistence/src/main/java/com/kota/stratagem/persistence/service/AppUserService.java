package com.kota.stratagem.persistence.service;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface AppUserService {

	AppUser create(String name, String passwordHash, String email, Role role) throws PersistenceServiceException;

	AppUser readElementary(Long id) throws PersistenceServiceException;

	AppUser readElementary(String username) throws PersistenceServiceException;

	AppUser readWithNotifications(Long id) throws PersistenceServiceException;

	AppUser readComplete(Long id) throws PersistenceServiceException;

	AppUser readComplete(String username) throws PersistenceServiceException;

	Set<AppUser> readByRole(Role role) throws PersistenceServiceException;

	Set<AppUser> readAll() throws PersistenceServiceException;

	AppUser update(Long id, String name, String passwordHash, String email, Role role, String modifier) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

	void addNotification(Long id, Notification notification) throws PersistenceServiceException;

}
