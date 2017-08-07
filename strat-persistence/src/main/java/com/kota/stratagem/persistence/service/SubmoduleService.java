package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface SubmoduleService {

	Submodule create(String name, String description, Date deadline, AppUser creator, Long project) throws PersistenceServiceException;

	Submodule readElementary(Long id) throws PersistenceServiceException;

	Submodule readWithTasks(Long id) throws PersistenceServiceException;

	Submodule readComplete(Long id) throws PersistenceServiceException;

	Set<Submodule> readAll() throws PersistenceServiceException;

	Submodule update(Long id, String name, String description, Date deadline, AppUser modifier) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}
