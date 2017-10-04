package com.kota.stratagem.ejbservice.interceptor;

import java.io.Serializable;
import java.util.Collection;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

@Certified
@Interceptor
public class CertifiedInterceptor implements Serializable {

	private static final long serialVersionUID = -2964232685671357803L;

	private static final Logger LOGGER = Logger.getLogger(CertifiedInterceptor.class);

	@AroundInvoke
	public Object certifyRepresentor(InvocationContext context) throws Exception {
		boolean certified = true;
		Object result = null;
		for(Object param : context.getParameters()) {
			if(param instanceof Collection<?>) {
				for(Object obj : (Collection<?>) param) {
					if(!this.certify(obj, context)) {
						certified = false;
					}
				}
			} else {
				if(!this.certify(param, context)) {
					certified = false;
				}
			}
		}
		if(certified) {
			result = context.proceed();
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Invalid Representor type! Extension management terminated.");
			}
		}
		return result;
	}

	private boolean certify(Object param, InvocationContext context) {
		return param.getClass().equals(context.getMethod().getAnnotation(Certified.class).value());
	}

}
