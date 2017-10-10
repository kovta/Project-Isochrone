package com.kota.stratagem.webservice.processing;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

@Path("/TaskSet")
public interface TaskRestService {

	@GET
	@Path("/{id}")
	@Produces("application/json")
	TaskRepresentor getTask(@PathParam("id") Long id) throws AdaptorException;

}
