package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

import org.apache.log4j.Logger;

public abstract class AbstractDTOExtensionManager implements DTOExtensionManager {

	protected static final Logger LOGGER = Logger.getLogger(AbstractDTOExtensionManager.class);

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
