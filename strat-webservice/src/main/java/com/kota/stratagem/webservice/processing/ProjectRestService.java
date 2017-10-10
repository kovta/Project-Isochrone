package com.kota.stratagem.webservice.processing;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;

@Path("/ProjectSet")
public interface ProjectRestService {

	@GET
	@Path("/{id}")
	@Produces("application/json")
	ProjectRepresentor getProject(@PathParam("id") Long id) throws AdaptorException;

	@GET
	@Produces("application/json")
	List<ProjectRepresentor> getProjects() throws AdaptorException;

}
