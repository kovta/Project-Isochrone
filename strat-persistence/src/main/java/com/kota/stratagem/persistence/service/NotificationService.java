package com.kota.stratagem.persistence.service;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface NotificationService {

	Notification create(Long inducer, String message) throws PersistenceServiceException;

	void dismiss(Long id, String operator) throws PersistenceServiceException;

}
