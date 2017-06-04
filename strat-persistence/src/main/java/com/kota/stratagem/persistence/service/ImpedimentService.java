package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Remedy;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.trunk.ImpedimentStatus;
import com.kota.stratagem.persistence.entity.trunk.Priority;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface ImpedimentService {

	Impediment create(String name, String description, Priority priority, ImpedimentStatus status, Date reportDate, AppUser reporter, AppUser processor,
			AppUser creator, Set<Remedy> remedies, Project project, Task task) throws PersistenceServiceException;

	Impediment read(Long id) throws PersistenceServiceException;

	Set<Impediment> readAll() throws PersistenceServiceException;

	Impediment update(Long id, String name, String description, Priority priority, ImpedimentStatus status, Date reportDate, AppUser reporter,
			AppUser processor, AppUser modifier, Set<Remedy> remedies, Project project, Task task) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}
