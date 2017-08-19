package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;

@Local
public interface NotificationProtocol {

	void saveNotification(Long inducer, String message, Long[] recipients) throws AdaptorException;

}
