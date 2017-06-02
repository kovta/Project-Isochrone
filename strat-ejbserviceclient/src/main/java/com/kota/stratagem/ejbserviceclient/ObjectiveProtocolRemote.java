package com.kota.stratagem.ejbserviceclient;

import javax.ejb.Remote;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;

@Remote
public interface ObjectiveProtocolRemote {
	
	ObjectiveRepresentor getObjective(Long id) throws ServiceException;
	
}