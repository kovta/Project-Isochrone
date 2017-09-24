package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

public abstract class AbstractDTOExtensionManager implements DTOExtensionManager {

	protected static final Logger LOGGER = Logger.getLogger(AbstractDTOExtensionManager.class);

	@Override
	public AppUserRepresentor prepare(AppUserRepresentor representor) {
		this.sortBaseCollections();
		return representor;
	}

	@Override
	public List<AppUserRepresentor> prepareAppUsers(List<AppUserRepresentor> representors) {
		this.sortJointCollection();
		return representors;
	}

	@Override
	public TeamRepresentor prepare(TeamRepresentor representor) {
		this.sortBaseCollections();
		return representor;
	}

	@Override
	public List<TeamRepresentor> prepareTeams(List<TeamRepresentor> representors) {
		this.sortJointCollection();
		return representors;
	}

	@Override
	public ObjectiveRepresentor prepare(ObjectiveRepresentor representor) {
		this.addRepresentorSpecificProperties();
		this.sortSpecializedCollections();
		this.sortBaseCollections();
		return representor;
	}

	@Override
	public List<ObjectiveRepresentor> prepareObjectives(List<ObjectiveRepresentor> representors) {
		this.sortJointCollection();
		return representors;
	}

	@Override
	public ProjectRepresentor prepare(ProjectRepresentor representor) {
		this.addRepresentorSpecificProperties();
		this.sortSpecializedCollections();
		this.sortBaseCollections();
		return representor;
	}

	@Override
	public List<ObjectiveRepresentor> prepareProjectClusters(List<ObjectiveRepresentor> representors) {
		this.sortJointCollection();
		return representors;
	}

	@Override
	public SubmoduleRepresentor prepare(SubmoduleRepresentor representor) {
		this.addRepresentorSpecificProperties();
		this.sortSpecializedCollections();
		this.sortBaseCollections();
		return representor;
	}

	@Override
	public TaskRepresentor prepare(TaskRepresentor representor) {
		this.addRepresentorSpecificProperties();
		this.sortSpecializedCollections();
		this.sortBaseCollections();
		return representor;
	}

	@Override
	public List<TaskRepresentor> prepareCompliantTasks(List<TaskRepresentor> representors) {
		this.sortJointCollection();
		return representors;
	}

	protected abstract void addRepresentorSpecificProperties();

	protected abstract void sortSpecializedCollections();

	protected abstract void sortBaseCollections();

	protected abstract void sortJointCollection();

}
