package com.kota.stratagem.ejbservice.interceptor;

import java.io.Serializable;
import java.util.Collection;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.InvalidRepresentorException;

@Certified
@Interceptor
public class CertifiedInterceptor implements Serializable {

	private static final long serialVersionUID = -2964232685671357803L;

	private static final Logger LOGGER = Logger.getLogger(CertifiedInterceptor.class);

	@AroundInvoke
	public Object certifyRepresentor(InvocationContext context) throws Exception {
		boolean certified = true;
		Object result = null;
		for (final Object param : context.getParameters()) {
			if (param instanceof Collection<?>) {
				for (final Object obj : (Collection<?>) param) {
					if (!this.certify(obj, context)) {
						certified = false;
					}
				}
			} else {
				if (!this.certify(param, context)) {
					certified = false;
				}
			}
		}
		if (certified) {
			result = context.proceed();
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Invalid Representor type! Extension management terminated.");
			}
			throw new InvalidRepresentorException();
		}
		return result;
	}

	private boolean certify(Object param, InvocationContext context) {
		return param.getClass().equals(context.getMethod().getAnnotation(Certified.class).value());
	}

}
