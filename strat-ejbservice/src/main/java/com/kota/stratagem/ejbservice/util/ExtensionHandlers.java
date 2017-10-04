package com.kota.stratagem.ejbservice.util;

import java.util.Map;

import com.kota.stratagem.ejbservice.preparation.AppUserExtensionManager;
import com.kota.stratagem.ejbservice.preparation.ObjectiveExtensionManager;
import com.kota.stratagem.ejbservice.preparation.ProjectExtensionManager;
import com.kota.stratagem.ejbservice.preparation.SubmoduleExtensionManager;
import com.kota.stratagem.ejbservice.preparation.TaskExtensionManager;
import com.kota.stratagem.ejbservice.preparation.TeamExtensionManager;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

public class ExtensionHandlers {

	public static final Map<Class<?>, Class<?>> extensionHandlers = PopulateExtensionHandlers();

	private static Map<Class<?>, Class<?>> PopulateExtensionHandlers() {

		extensionHandlers.put(AppUserRepresentor.class, AppUserExtensionManager.class);

		extensionHandlers.put(TeamRepresentor.class, TeamExtensionManager.class);

		extensionHandlers.put(ObjectiveRepresentor.class, ObjectiveExtensionManager.class);

		extensionHandlers.put(ProjectRepresentor.class, ProjectExtensionManager.class);

		extensionHandlers.put(SubmoduleRepresentor.class, SubmoduleExtensionManager.class);

		extensionHandlers.put(TaskRepresentor.class, TaskExtensionManager.class);

		return extensionHandlers;
	}

}
