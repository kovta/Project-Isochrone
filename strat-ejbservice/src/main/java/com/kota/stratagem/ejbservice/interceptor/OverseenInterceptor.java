package com.kota.stratagem.ejbservice.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbserviceclient.domain.designation.Dispatchable;
import com.kota.stratagem.persistence.util.Constants;

@Deprecated
@Overseen
@Interceptor
public class OverseenInterceptor implements Serializable {

	private static final long serialVersionUID = 8540092019212607348L;

	private static final Logger LOGGER = Logger.getLogger(OverseenInterceptor.class);

	@Inject
	private LifecycleOverseer overseer;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@AroundInvoke
	public Object overseeLifecycleDevelopment(InvocationContext context) throws Exception {
		final Object result;
		result = context.proceed();
		if (result instanceof Dispatchable) {
			switch (context.getMethod().getAnnotation(Overseen.class).operationType()) {
				case CREATION:
					this.overseer.created(((Dispatchable) result).toTextMessage());
					break;
				case ASSIGNMENT:
					this.overseer.assigned(((Dispatchable) result).toTextMessage());
					break;
				case CONFIGURATION:
					break;
				case DECONFIGURATION:
					break;
				case DELETION:
					this.overseer.deleted(((Dispatchable) result).toTextMessage() + Constants.PAYLOAD_SEPARATOR
							+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName());
					break;
				case DISSOCIATION:
					this.overseer.dissociated(((Dispatchable) result).toTextMessage());
					break;
				case MODIFICATION:
					break;
				default:
					break;
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Result Object is not dispatchable. Message dispatching termianted.");
			}
		}
		return result;
	}

}
