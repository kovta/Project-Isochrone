package com.kota.stratagem.webservice.processing;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ProjectProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;

public class ProjectRestServiceBean implements ProjectRestService {

	private static final Logger LOGGER = Logger.getLogger(ProjectRestServiceBean.class);

	@Inject
	private ProjectProtocol protocol;

	@Override
	public ProjectRepresentor getProject(Long id) throws AdaptorException {
		LOGGER.info("Get Project by id: (" + id + ")");
		return this.protocol.getProject(id);
	}

	@Override
	public List<ProjectRepresentor> getProjects() throws AdaptorException {
		LOGGER.info("Get Projects");
		return this.protocol.getAllProjects(null);
	}

}
