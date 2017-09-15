package com.kota.stratagem.ejbservice.access;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SessionContextAccessorImpl implements SessionContextAccessor, Serializable {

	private static final long serialVersionUID = 1369955564197285346L;

	@Resource
	private SessionContext context;

	@Override
	public SessionContext getSessionContext() {
		return this.context;
	}

}
