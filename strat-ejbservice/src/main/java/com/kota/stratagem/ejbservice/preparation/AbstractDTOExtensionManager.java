package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

import javax.inject.Inject;

import com.kota.stratagem.ejbservice.qualifier.NormalDistributionBased;
import com.kota.stratagem.ejbservice.qualifier.Shared;
import com.kota.stratagem.ejbservice.statistics.ProbabilityCalculator;

public abstract class AbstractDTOExtensionManager implements DTOExtensionManager {

	@Inject
	@Shared
	protected ExtensionProvider provider;

	@Inject
	@NormalDistributionBased
	protected ProbabilityCalculator calculator;

	@Override
	public Object prepare(Object representor) {
		this.addRepresentorSpecificProperties();
		this.sortSpecializedCollections();
		this.sortBaseCollections();
		return representor;
	}

	@Override
	public Object prepareForOwner(Object representor) {
		this.addOwnerDependantProperties();
		return representor;
	}

	@Override
	public List<?> prepareBatch(List<?> representors) {
		this.sortJointCollection();
		return representors;
	}

	protected abstract void addRepresentorSpecificProperties();

	protected abstract void addOwnerDependantProperties();

	protected abstract void sortSpecializedCollections();

	protected abstract void sortBaseCollections();

	protected abstract void sortJointCollection();

}
