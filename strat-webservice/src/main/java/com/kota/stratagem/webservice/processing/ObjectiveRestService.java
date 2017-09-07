package com.kota.stratagem.webservice.processing;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;

@Path("/ObjectiveSet")
public interface ObjectiveRestService {

	@GET
	@Path("/{id}")
	@Produces("application/json")
	ObjectiveRepresentor getObjective(@PathParam("id") Long id) throws ServiceException;

	@GET
	@Produces("application/json")
	List<ObjectiveRepresentor> getObjectives() throws ServiceException, AdaptorException;

}