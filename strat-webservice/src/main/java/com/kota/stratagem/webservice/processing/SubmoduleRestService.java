package com.kota.stratagem.webservice.processing;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;

@Path("/SubmoduleSet")
public interface SubmoduleRestService {

	@GET
	@Path("/{id}")
	@Produces("application/json")
	SubmoduleRepresentor getSubmodule(@PathParam("id") Long id) throws AdaptorException;

}
