package com.kota.stratagem.webservice.processing;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;

@Stateless
public class AppUserRestServiceBean implements AppUserRestService {

	private static final Logger LOGGER = Logger.getLogger(AppUserRestServiceBean.class);

	@EJB
	private AppUserProtocol protocol;

	@Override
	public List<AppUserRepresentor> getAppUsers() throws AdaptorException {
		LOGGER.info("Get AppUsers");
		return this.protocol.getAllAppUsers();
	}

}
