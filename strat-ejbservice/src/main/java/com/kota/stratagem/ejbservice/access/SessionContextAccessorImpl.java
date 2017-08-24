package com.kota.stratagem.ejbservice.access;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionContextAccessorImpl implements SessionContextAccessor {

	@Resource
	private SessionContext context;

	@Override
	public SessionContext getSessionContext() {
		return this.context;
	}

}
