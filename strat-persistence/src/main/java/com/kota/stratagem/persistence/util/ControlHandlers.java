package com.kota.stratagem.persistence.util;

import java.util.Map;

import com.kota.stratagem.persistence.service.delegation.AbstractAppUserAssignmentService;
import com.kota.stratagem.persistence.service.delegation.AppUserObjectiveAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.AppUserProjectAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.AppUserSubmoduleAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.AppUserTaskAssignmentServiceImpl;

public class ControlHandlers {

	public static final Map<String, Class<? extends AbstractAppUserAssignmentService>> serviceMap = PopulateServiceMap();

	private static Map<String, Class<? extends AbstractAppUserAssignmentService>> PopulateServiceMap() {

		serviceMap.put(Constants.OBJECTIVE_DATA_NAME, AppUserObjectiveAssignmentServiceImpl.class);

		serviceMap.put(Constants.PROJECT_DATA_NAME, AppUserProjectAssignmentServiceImpl.class);

		serviceMap.put(Constants.SUBMODULE_DATA_NAME, AppUserSubmoduleAssignmentServiceImpl.class);

		serviceMap.put(Constants.TASK_DATA_NAME, AppUserTaskAssignmentServiceImpl.class);

		return serviceMap;
	}

}
