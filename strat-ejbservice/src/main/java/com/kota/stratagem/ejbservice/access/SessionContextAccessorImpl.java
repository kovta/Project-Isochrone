package com.kota.stratagem.ejbservice.access;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
public class SessionContextAccessorImpl implements SessionContextAccessor {

	@Resource
	private SessionContext context;

	@Override
	public SessionContext getSessionContext() {
		return this.context;
	}

}
