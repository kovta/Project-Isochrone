package com.kota.stratagem.ejbservice.domain;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

public class TaskDependencyLayer extends AbstractDependencyLayer {

	private List<TaskRepresentor> dependencies;

	public TaskDependencyLayer(int level, List<TaskRepresentor> dependencies) {
		super(level);
		this.dependencies = dependencies;
	}

	public List<TaskRepresentor> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<TaskRepresentor> dependencies) {
		this.dependencies = dependencies;
	}

	public void addDependencies(List<TaskRepresentor> dependencies) {
		this.dependencies.addAll(dependencies);
	}

}
