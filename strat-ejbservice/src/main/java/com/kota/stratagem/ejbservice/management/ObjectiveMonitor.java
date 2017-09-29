package com.kota.stratagem.ejbservice.management;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;

public class ObjectiveMonitor implements ObjectiveMonitorMBean {

	private static final Logger LOGGER = Logger.getLogger(ObjectiveMonitor.class);

	@Inject
	ObjectiveProtocol protocol;

	public void start() throws Exception {
		LOGGER.info("Start Stratagem MBean");
	}

	public void stop() throws Exception {
		LOGGER.info("Stop Stratagem MBean");
	}

	@Override
	public int countObjectives() throws AdaptorException {
		try {
			return this.protocol.getAllObjectives().size();
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
			return -1;
		}
	}

}
