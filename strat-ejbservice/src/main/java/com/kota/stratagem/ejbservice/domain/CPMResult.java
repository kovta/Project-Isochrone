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
		return expectedDuration;
	}

	public Double getStandardDeviation() {
		return standardDeviation;
	}

}