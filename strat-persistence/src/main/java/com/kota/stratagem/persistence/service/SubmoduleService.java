package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;

@Local
public interface SubmoduleService {

	Submodule create(String name, String description, Date deadline, AppUser creator, Long project);

	Submodule readElementary(Long id);

	Submodule readWithAssignments(Long id);

	Submodule readWithTasks(Long id);

	Submodule readComplete(Long id);

	Set<Submodule> readAll();

	Submodule update(Long id, String name, String description, Date deadline, AppUser modifier);

	void delete(Long id) throws CoherentPersistenceServiceException;

	boolean exists(Long id);

}
