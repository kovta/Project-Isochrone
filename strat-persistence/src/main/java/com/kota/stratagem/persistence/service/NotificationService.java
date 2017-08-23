package com.kota.stratagem.persistence.service;

import javax.ejb.Local;

import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface NotificationService {

	void create(Long inducer, String message, Long[] recipients) throws PersistenceServiceException;

	void dismiss(Long id, String operator) throws PersistenceServiceException;

}
