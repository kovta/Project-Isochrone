package com.kota.stratagem.webservice.processing;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;

@Path("/AppUserSet")
public interface AppUserRestService {

	@GET
	@Produces("application/json")
	List<AppUserRepresentor> getAppUsers() throws AdaptorException;

}
