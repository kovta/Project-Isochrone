package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

public abstract class AbstractDTOExtensionManager implements DTOExtensionManager {

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

	protected abstract void addRepresentorSpecificProperties();

	protected abstract void sortSpecializedCollections();

	protected abstract void sortBaseCollections();

	protected abstract void sortJointCollection();

}
