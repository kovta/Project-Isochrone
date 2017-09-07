package com.kota.stratagem.persistence.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Contained
@Interceptor
public class ContainedInterceptor implements Serializable {

	private static final long serialVersionUID = 3879241747425015042L;

	private static final Logger LOGGER = Logger.getLogger(ContainedInterceptor.class);

	@AroundInvoke
	public Object logMethodInvocations(InvocationContext context) throws Exception {
		final StringBuilder info = new StringBuilder();
		info.append(context.getTarget().getClass().getName()).append(".").append(context.getMethod().getName()).append("(");
		final String signature = info.toString() + ")";
		final Object[] parameters = context.getParameters();
		if (parameters != null) {
			int i = parameters.length - 1;
			for (final Object parameter : parameters) {
				info.append(parameter.toString());
				if (i > 0) {
					info.append(",");
				}
				i--;
			}
		}
		info.append(")");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Invoking: " + info.toString());
		}
		final long start = System.currentTimeMillis();
		final Object result;
		try {
			result = context.proceed();
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during the invocation of: " + signature
					+ ((parameters.length) > 0 ? (" with identifier: (" + parameters[0].toString()) + ")! " : "! ") + e.getLocalizedMessage(), e);
		}
		final long end = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Concluding: " + signature);
			LOGGER.debug("Execution time: " + (end - start) + " millisecond(s)");
		}
		return result;
	}

}
