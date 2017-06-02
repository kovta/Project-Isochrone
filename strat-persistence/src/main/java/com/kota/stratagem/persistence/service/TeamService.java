package com.kota.stratagem.persistence.service;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface TeamService {

	Team read(Long id) throws PersistenceServiceException;

}
