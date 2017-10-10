package com.kota.stratagem.webservice.processing;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.SubmoduleProtocol;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;

public class SubmoduleRestServiceBean implements SubmoduleRestService {

	private static final Logger LOGGER = Logger.getLogger(SubmoduleRestServiceBean.class);

	@Inject
	private SubmoduleProtocol protocol;

	@Override
	public SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException {
		LOGGER.info("Get Submodule by id: (" + id + ")");
		return this.protocol.getSubmodule(id);
	}

}
