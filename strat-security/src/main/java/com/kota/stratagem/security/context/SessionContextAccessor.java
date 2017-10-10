package com.kota.stratagem.security.context;

import javax.ejb.Local;
import javax.ejb.SessionContext;

@Local
public interface SessionContextAccessor {

	public SessionContext getSessionContext();

}
