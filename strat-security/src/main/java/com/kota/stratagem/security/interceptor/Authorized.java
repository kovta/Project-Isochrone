package com.kota.stratagem.security.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

import com.kota.stratagem.security.domain.RestrictionLevel;

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Authorized {

	@Nonbinding
	RestrictionLevel value() default RestrictionLevel.SYSTEM_ADMINISTRATOR_LEVEL;

}
