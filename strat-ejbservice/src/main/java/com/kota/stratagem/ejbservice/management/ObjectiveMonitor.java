package com.kota.stratagem.ejbservice.management;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;

public class ObjectiveMonitor implements ObjectiveMonitorMBean {

	private static final Logger LOGGER = Logger.getLogger(ObjectiveMonitor.class);

	@EJB
	ObjectiveProtocol protocol;

	public void start() throws Exception {
		LOGGER.info("Start Ticketing MBean");
	}

	public void stop() throws Exception {
		LOGGER.info("Stop Ticketing MBean");
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
