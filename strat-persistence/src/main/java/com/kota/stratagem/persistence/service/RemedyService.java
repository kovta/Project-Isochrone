package com.kota.stratagem.persistence.service;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Remedy;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface RemedyService {

	Remedy read(Long id) throws PersistenceServiceException;

}
