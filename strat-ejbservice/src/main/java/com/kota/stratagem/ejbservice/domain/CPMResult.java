package com.kota.stratagem.ejbservice.domain;

public class CPMResult {

	private final Double expectedDuration;
	private final Double standardDeviation;

	public CPMResult(Double expectedDuration, Double standardDeviation) {
		super();
		this.expectedDuration = expectedDuration;
		this.standardDeviation = standardDeviation;
	}

	public Double getExpectedDuration() {
		return this.expectedDuration;
	}

	public Double getStandardDeviation() {
		return this.standardDeviation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.expectedDuration == null) ? 0 : this.expectedDuration.hashCode());
		result = (prime * result) + ((this.standardDeviation == null) ? 0 : this.standardDeviation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final CPMResult other = (CPMResult) obj;
		if (this.expectedDuration == null) {
			if (other.expectedDuration != null) {
				return false;
			}
		} else if (!this.expectedDuration.equals(other.expectedDuration)) {
			return false;
		}
		if (this.standardDeviation == null) {
			if (other.standardDeviation != null) {
				return false;
			}
		} else if (!this.standardDeviation.equals(other.standardDeviation)) {
			return false;
		}
		return true;
	}

}