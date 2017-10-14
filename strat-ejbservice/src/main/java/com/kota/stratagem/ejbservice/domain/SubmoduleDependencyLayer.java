package com.kota.stratagem.ejbservice.domain;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;

public class SubmoduleDependencyLayer extends AbstractDependencyLayer {

	private List<SubmoduleRepresentor> dependencies;

	public SubmoduleDependencyLayer(int level, List<SubmoduleRepresentor> dependencies) {
		super(level);
		this.dependencies = dependencies;
	}

	public List<SubmoduleRepresentor> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<SubmoduleRepresentor> dependencies) {
		this.dependencies = dependencies;
	}

	public void addDependencies(List<SubmoduleRepresentor> dependencies) {
		this.dependencies.addAll(dependencies);
	}

}
