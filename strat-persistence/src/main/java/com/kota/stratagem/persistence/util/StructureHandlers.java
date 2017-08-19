package com.kota.stratagem.persistence.util;

import java.util.Map;

import com.kota.stratagem.persistence.service.delegation.AbstractAppUserAssignmentService;
import com.kota.stratagem.persistence.service.delegation.AppUserObjectiveAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.AppUserProjectAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.AppUserSubmoduleAssignmentServiceImpl;
import com.kota.stratagem.persistence.service.delegation.AppUserTaskAssignmentServiceImpl;

public class StructureHandlers {

	public static final Map<String, Class<? extends AbstractAppUserAssignmentService>> structureMap = PopulateMap();

	private static Map<String, Class<? extends AbstractAppUserAssignmentService>> PopulateMap() {

		structureMap.put(Constants.OBJECTIVE_DATA_NAME, AppUserObjectiveAssignmentServiceImpl.class);

		structureMap.put(Constants.PROJECT_DATA_NAME, AppUserProjectAssignmentServiceImpl.class);

		structureMap.put(Constants.SUBMODULE_DATA_NAME, AppUserSubmoduleAssignmentServiceImpl.class);

		structureMap.put(Constants.TASK_DATA_NAME, AppUserTaskAssignmentServiceImpl.class);

		return structureMap;
	}

}
