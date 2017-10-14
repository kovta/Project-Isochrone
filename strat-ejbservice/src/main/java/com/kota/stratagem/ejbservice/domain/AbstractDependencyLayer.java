package com.kota.stratagem.ejbservice.domain;

public abstract class AbstractDependencyLayer {

	private int level;

	public AbstractDependencyLayer(int level) {
		super();
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + level;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		AbstractDependencyLayer other = (AbstractDependencyLayer) obj;
		if(level != other.level) {
			return false;
		}
		return true;
	}

}
