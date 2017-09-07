package com.kota.stratagem.ejbservice.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;

@Regulated
@Interceptor
public class RegulatedInterceptor implements Serializable {

	private static final long serialVersionUID = 6267689322946614470L;

	private static final Logger LOGGER = Logger.getLogger(RegulatedInterceptor.class);

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
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
		final long end = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Concluding: " + signature);
			LOGGER.debug("Execution time: " + (end - start) + " millisecond(s)");
		}
		return result;
	}

}
