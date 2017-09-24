package com.kota.stratagem.weblayer.common.task;

import com.kota.stratagem.weblayer.common.assignment.AssignableAttribute;

public interface TaskAttribute extends AssignableAttribute {

	public static final String ATTR_TASK = "task";
	public static final String ATTR_CONFIGURABLE_DEPENDENCIES = "configurableDependencies";
	public static final String ATTR_ERROR = "tsk-error";
	public static final String ATTR_SUCCESS = "tsk-success";

}
