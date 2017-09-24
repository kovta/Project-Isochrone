package com.kota.stratagem.persistence.util;

import java.util.Map;

import com.kota.stratagem.persistence.service.delegation.individual.AbstractAppUserAssignmentService;
import com.kota.stratagem.persistence.service.delegation.individual.AppUserObjectiveAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.individual.AppUserProjectAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.individual.AppUserSubmoduleAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.individual.AppUserTaskAssignmentServiceImpl;

public class StructureHandlers {

	public static final Map<String, Class<? extends AbstractAppUserAssignmentService>> serviceMap = PopulateServiceMap();

	private static Map<String, Class<? extends AbstractAppUserAssignmentService>> PopulateServiceMap() {

		serviceMap.put(Constants.OBJECTIVE_DATA_LABEL, AppUserObjectiveAssignmentServiceImpl.class);

		serviceMap.put(Constants.PROJECT_DATA_LABEL, AppUserProjectAssignmentServiceImpl.class);

		serviceMap.put(Constants.SUBMODULE_DATA_LABEL, AppUserSubmoduleAssignmentServiceImpl.class);

		serviceMap.put(Constants.TASK_DATA_LABEL, AppUserTaskAssignmentServiceImpl.class);

		return serviceMap;
	}

}
