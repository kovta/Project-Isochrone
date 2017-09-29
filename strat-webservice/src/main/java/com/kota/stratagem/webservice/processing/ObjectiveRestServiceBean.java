package com.kota.stratagem.webservice.processing;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;

@Stateless
public class ObjectiveRestServiceBean implements ObjectiveRestService {

	private static final Logger LOGGER = Logger.getLogger(ObjectiveRestServiceBean.class);

	@Inject
	private ObjectiveProtocol protocol;

	@Override
	public ObjectiveRepresentor getObjective(Long id) throws ServiceException {
		LOGGER.info("Get objective by id: (" + id + ")");
		return this.protocol.getObjective(id);
	}

	@Override
	public List<ObjectiveRepresentor> getObjectives() throws ServiceException, AdaptorException {
		LOGGER.info("Get Objectives");
		return this.protocol.getAllObjectives();
	}

}
