package com.kota.stratagem.webservice.processing;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.TaskProtocol;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

public class TaskRestServiceBean implements TaskRestService {

	private static final Logger LOGGER = Logger.getLogger(TaskRestServiceBean.class);

	@Inject
	private TaskProtocol protocol;

	@Override
	public TaskRepresentor getTask(Long id) throws AdaptorException {
		LOGGER.info("Get Task by id: (" + id + ")");
		return this.protocol.getTask(id);
	}

}
