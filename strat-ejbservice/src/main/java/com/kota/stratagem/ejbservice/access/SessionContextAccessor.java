package com.kota.stratagem.ejbservice.access;

import javax.ejb.Local;
import javax.ejb.SessionContext;

@Local
public interface SessionContextAccessor {

	public SessionContext getSessionContext();

}
