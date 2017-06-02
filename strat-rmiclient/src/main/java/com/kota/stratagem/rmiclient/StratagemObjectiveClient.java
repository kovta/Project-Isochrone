package com.kota.stratagem.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbserviceclient.ObjectiveProtocolRemote;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;

public class StratagemObjectiveClient {

	private static final String JBOSS_INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	private static final String JBOSS_PROVIDER_URL = "remote://localhost:4447";
	private static final String JBOSS_URL_PKG_PREFIXES = "org.jboss.ejb.client.naming";

	private static final String JBOSS_NAMING_CLIENT_EJB_CONTEXT_KEY = "jboss.naming.client.ejb.context";
	private static final String JBOSS_NAMING_CLIENT_EJB_CONTEXT_VALUE = "true";

	private static final Logger LOGGER = Logger.getLogger(StratagemObjectiveClient.class);

	public static void main(final String[] args) throws Exception {
		System.out.println(new StratagemObjectiveClient().invoke(0L));
	}

	private ObjectiveRepresentor invoke(final Long id) {
		ObjectiveRepresentor objective = null;
		try {
			final ObjectiveProtocolRemote protocol = this.lookup();
			objective = protocol.getObjective(id);
			LOGGER.info(objective);
		} catch (final ServiceException e) {
			e.printStackTrace();
		} catch (final NamingException e) {
			e.printStackTrace();
		}
		return objective;
	}

	private ObjectiveProtocolRemote lookup() throws NamingException {
		final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, JBOSS_INITIAL_CONTEXT_FACTORY);
		jndiProperties.put(Context.PROVIDER_URL, JBOSS_PROVIDER_URL);
		jndiProperties.put(Context.URL_PKG_PREFIXES, JBOSS_URL_PKG_PREFIXES);
		jndiProperties.put(JBOSS_NAMING_CLIENT_EJB_CONTEXT_KEY, JBOSS_NAMING_CLIENT_EJB_CONTEXT_VALUE);
		final Context context = new InitialContext(jndiProperties);
		return (ObjectiveProtocolRemote) context
				.lookup("stratagem/strat-ejbservice/ObjectiveProtocolImpl!com.kota.stratagem.ejbserviceclient.ObjectiveProtocolRemote");
	}

}