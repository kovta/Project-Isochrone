package com.kota.stratagem.security.interceptor;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.security.context.SessionContextAccessor;
import com.kota.stratagem.security.domain.RestrictionLevel;
import com.kota.stratagem.security.exception.InvalidRestrictionLevelException;
import com.kota.stratagem.security.exception.UnauthorizedControlInvocationException;
import com.kota.stratagem.security.util.AuthorizationLevels;

@Authorized
@Interceptor
public class AuthorizedInterceptor implements Serializable {

	private static final long serialVersionUID = -2964232685671357803L;

	private static final Logger LOGGER = Logger.getLogger(AuthorizedInterceptor.class);

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@AroundInvoke
	public Object authorizeMethodInvocation(InvocationContext context) throws Exception {
		boolean authorized = true;
		Object result = null;
		RestrictionLevel restriction = null, methodAnnotationParameter = null, classAnnotationParameter = null;
		for(Annotation annotation : context.getMethod().getAnnotations()) {
			if(annotation.annotationType().equals(Authorized.class)) {
				methodAnnotationParameter = context.getMethod().getAnnotation(Authorized.class).value();
			}
		}
		for(Annotation annotation : context.getMethod().getDeclaringClass().getAnnotations()) {
			if(annotation.annotationType().equals(Authorized.class)) {
				classAnnotationParameter = context.getMethod().getDeclaringClass().getAnnotation(Authorized.class).value();
			}
		}
		if(classAnnotationParameter != null) {
			restriction = classAnnotationParameter;
		}
		if(methodAnnotationParameter != null) {
			restriction = methodAnnotationParameter;
		}
		if((methodAnnotationParameter == null) && (classAnnotationParameter == null)) {
			throw new InvalidRestrictionLevelException();
		}
		if (!this.authorize(restriction)) {
			authorized = false;
		}
		if (authorized) {
			result = context.proceed();
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Insufficient user privileges for invoker principal!");
			}
			throw new UnauthorizedControlInvocationException();
		}
		return result;
	}

	private boolean authorize(RestrictionLevel restriction) {
		boolean authorized = false;
		for(String role : AuthorizationLevels.getAuthorityRestrictions().get(restriction)) {
			if(this.sessionContextAccessor.getSessionContext().isCallerInRole(role)) {
				authorized = true;
			}
		}
		return authorized;
	}

}
